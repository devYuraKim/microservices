package com.example.accounts.mapper;

import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Customer;

public class CustomerMapper {

    // Private constructor prevents instantiation
    private CustomerMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Creating new instances:
    // - DTO: safe for all CRUD operations
    // - Entity: safe only for Create (C); for Read, Update, Delete, use existing instances
    public static CustomerDto mapToCustomerDto(Customer entity, CustomerDto dto) {
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setMobileNumber(entity.getMobileNumber());
        return dto;
    }

    public static Customer mapToCustomer(CustomerDto dto, Customer entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMobileNumber(dto.getMobileNumber());
        return entity;
    }

    public static void updateCustomer(CustomerDto dto, Customer entity) {
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        // 🔮 Add more fields as needed
    }

}
