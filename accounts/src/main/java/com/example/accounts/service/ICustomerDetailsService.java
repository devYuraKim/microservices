package com.example.accounts.service;

import com.example.accounts.dto.CustomerDetailsDto;

public interface ICustomerDetailsService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);

}
