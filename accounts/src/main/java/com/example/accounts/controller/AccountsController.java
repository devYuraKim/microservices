package com.example.accounts.controller;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ErrorResponseDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// TODO[API]: Switch to ApiResponse<T> to unify success/error response structure
@Tag(
    name = "CRUD REST APIs for Accounts",
    description = "CREATE, FETCH, UPDATE and DELETE account details")
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    private final IAccountsService iAccountsService;

    private ResponseEntity<ResponseDto> buildSuccessResponse(HttpStatus httpStatus, String status, String message){
        return ResponseEntity.status(httpStatus).body(new ResponseDto(status, message));
    }

    @Operation(summary = "Create Account", description = "Create a new Customer and Account")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created"),
        @ApiResponse(responseCode = "409", description = "HTTP Status 409 Conflict", content=@Content(schema=@Schema(implementation=ErrorResponseDto.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return buildSuccessResponse(HttpStatus.CREATED, AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201);
    }

    @Operation(summary = "Fetch Account details", description = "Fetch Customer and Account details based on the given mobile number")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
        @ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content=@Content(schema=@Schema(implementation=ErrorResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content=@Content(schema=@Schema(implementation=ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")                                                     String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.ok(customerDto);
    }

    @Operation(summary = "Update Account details", description = "Update Customer and Account details based on the given account number")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
        @ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content=@Content(schema=@Schema(implementation=ErrorResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content=@Content(schema=@Schema(implementation=ErrorResponseDto.class)))
    })
    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.updateAccount(customerDto);
        return buildSuccessResponse(HttpStatus.OK, AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200);
    }

    @Operation(summary = "Delete Customer and Account details", description = "Delete Customer and Account details based on the given mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content=@Content(schema=@Schema(implementation=ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")                                                         String mobileNumber) {
        iAccountsService.deleteAccount(mobileNumber);
        return buildSuccessResponse(HttpStatus.OK, AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200);
    }
}
