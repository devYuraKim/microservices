package com.example.accounts.service.impl;

import com.example.accounts.config.FeatureFlags;
import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountsMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    //private, final: make dependency injections safe, immutable, and thread-safe
    //Foo foo: creating slot in memory(variable) for dependency injection
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final FeatureFlags featureFlags;
    //@AllArgsConstructor is used to inject dependencies; this.foo = foo; (assignment) this would be inside the constructor if there were no @AllArgsConstructor
    //Spring passes in beans into constructor via parameters; they are assigned to private final fields for encapsulation(no modifying outside the class) and immutability
    //no new Class() is used to create dependencies; Spring provides the existing beans

    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {
        //check before mapping
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                    + customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.mapToCustomerEntity(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    @Transactional
    public void updateAccount(CustomerDto customerDto) {
        AccountsDto accountsDto = customerDto.getAccountsDto();
        // Guard clause pattern: reduces nesting and makes intent clearer.
        // Input validation: prevents null pointer exceptions
        if (accountsDto == null) {
            throw new IllegalArgumentException("AccountsDto must not be null");
        }
        Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
        );

        // NOTE: customize update logic per business rules instead of updating all fields
        AccountsMapper.mapToAccountsEntity(accountsDto, accounts);
        accounts = accountsRepository.save(accounts);

        Long customerId = accounts.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
        );
        // NOTE: customize update logic per business rules instead of updating all fields
        CustomerMapper.mapToCustomerEntity(customerDto, customer);
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteAccount(String mobileNumber) {
        if (FeatureFlags.DELETE_ACCOUNT_EXCEPTION_SIMULATION.equals(mobileNumber) &&
                featureFlags.isFeatureEnabled(FeatureFlags.DELETE_ACCOUNT_EXCEPTION_SIMULATION)) {
            throw new RuntimeException("DELETE_ACCOUNT_EXCEPTION_SIMULATION");
        }
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        //Accounts are dependent on Customer - if there’s no account, it’s not an error
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}