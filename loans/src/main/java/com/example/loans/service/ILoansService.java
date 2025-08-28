package com.example.loans.service;

import com.example.loans.dto.LoansDto;

public interface ILoansService {

    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    void updateLoan(LoansDto loansDto);

    void deleteLoan(String mobileNumber);


}
