package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create Account
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Fetch Account Details
     * @param mobileNumber
     * @return Accounts details based on the given mobile number
     */
    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);
}
