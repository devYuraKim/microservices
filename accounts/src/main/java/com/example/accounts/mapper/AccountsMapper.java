package com.example.accounts.mapper;


import com.example.accounts.dto.AccountsDto;
import com.example.accounts.entity.Accounts;

public class AccountsMapper {

    // Private constructor prevents instantiation
    private AccountsMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static AccountsDto mapToAccountsDto(Accounts accounts) {
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto) {
        Accounts accounts = new Accounts();
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
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
