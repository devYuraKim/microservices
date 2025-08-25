package com.example.accounts.mapper;

import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Customer;

public class CustomerMapper {

    // Private constructor prevents instantiation
    private CustomerMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public static void updateCustomer(CustomerDto dto, Customer entity) {
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        // 🔮 Add more fields as needed
    }

}
