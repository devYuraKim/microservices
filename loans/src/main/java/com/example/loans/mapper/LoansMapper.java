package com.example.loans.mapper;

import com.example.loans.dto.LoansDto;
import com.example.loans.entity.Loans;

public class LoansMapper {

    private LoansMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static LoansDto mapToLoansDto(Loans entity, LoansDto dto) {
        dto.setLoanNumber(entity.getLoanNumber());
        dto.setLoanType(entity.getLoanType());
        dto.setMobileNumber(entity.getMobileNumber());
        dto.setTotalLoan(entity.getTotalLoan());
        dto.setAmountPaid(entity.getAmountPaid());
        dto.setOutstandingAmount(entity.getOutstandingAmount());
        return dto;
    }

    public static Loans mapToLoansEntity(LoansDto dto, Loans entity) {
        entity.setLoanNumber(dto.getLoanNumber());
        entity.setLoanType(dto.getLoanType());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setTotalLoan(dto.getTotalLoan());
        entity.setAmountPaid(dto.getAmountPaid());
        entity.setOutstandingAmount(dto.getOutstandingAmount());
        return entity;
    }


}
