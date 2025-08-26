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

    //TODO:
    // -convert return type to void
    // -remove 'isUpdated' variable
    // -delegate repetitive field updates to mappers
    // -use guard clause pattern and avoid nesting
    @Override
    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {
        // Consider returning void or the updated entity instead of a boolean.
        // The `isUpdated` variable doesn’t add real value since failure already throws.
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            // Guard clause pattern – bail early - could simplify readability:
            // if (accountsDto == null) { return; }
            // This reduces nesting and makes intent clearer.
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccountsEntity(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);
            // Repeated null checks for each field → consider a mapper that applies partial updates.
            // This keeps business logic consistent and reduces boilerplate.
            // ✅ Delegate update logic to mapper
            // AccountsMapper.updateAccounts(accountsDto, accounts);
            // accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomerEntity(customerDto, customer);
            customerRepository.save(customer);
            // ✅ Delegate update logic to mapper
            // CustomerMapper.updateCustomer(customerDto, customer);
            // customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
        // Remove isUpdated → method either updates or throws.
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