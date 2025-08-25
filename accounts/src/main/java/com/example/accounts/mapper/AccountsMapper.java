package com.example.accounts.mapper;


import com.example.accounts.dto.AccountsDto;
import com.example.accounts.entity.Accounts;

public class AccountsMapper {

    // Private constructor prevents instantiation
    private AccountsMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Creating new instances:
    // - DTO: safe for all CRUD operations
    // - Entity: safe only for Create (C); for Read, Update, Delete, use existing instances
    public static AccountsDto mapToAccountsDto(Accounts entity, AccountsDto dto) {
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setAccountType(entity.getAccountType());
        dto.setBranchAddress(entity.getBranchAddress());
        return dto;
    }

    public static Accounts mapToAccounts(AccountsDto dto, Accounts entity) {
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setAccountType(dto.getAccountType());
        entity.setBranchAddress(dto.getBranchAddress());
        return entity;
    }

    public static void updateAccounts(AccountsDto dto, Accounts entity) {
        if (dto.getAccountType() != null) {
            entity.setAccountType(dto.getAccountType());
        }
        if (dto.getBranchAddress() != null) {
            entity.setBranchAddress(dto.getBranchAddress());
        }
        // 🔮 Easy to extend later:
        // if (dto.getSomeNewField() != null) {
        //     entity.setSomeNewField(dto.getSomeNewField());
        // }
    }
}
