package com.example.accounts.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {
        //check before mapping
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "
                    + customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts));
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

            if (accountsDto.getAccountType() != null) {
                accounts.setAccountType(accountsDto.getAccountType());
            }
            if (accountsDto.getBranchAddress() != null) {
                accounts.setBranchAddress(accountsDto.getBranchAddress());
            }
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

            if (customerDto.getEmail() != null) {
                customer.setEmail(customerDto.getEmail());
            }
            customerRepository.save(customer);
            // ✅ Delegate update logic to mapper
            // CustomerMapper.updateCustomer(customerDto, customer);
            // customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
        // Remove isUpdated → method either updates or throws.
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