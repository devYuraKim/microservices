package com.example.accounts.controller;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    private final IAccountsService iAccountsService;

    private ResponseEntity<ResponseDto> buildSuccessResponse(HttpStatus httpStatus, String status, String message){
        return ResponseEntity.status(httpStatus).body(new ResponseDto(status, message));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return buildSuccessResponse(HttpStatus.CREATED, AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto) {
        iAccountsService.updateAccount(customerDto);
        return buildSuccessResponse(HttpStatus.OK, AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber) {
        iAccountsService.deleteAccount(mobileNumber);
        return buildSuccessResponse(HttpStatus.OK, AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200);
    }
}
