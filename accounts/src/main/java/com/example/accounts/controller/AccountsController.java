package com.example.accounts.controller;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.AccountsContactInfoDto;
import com.example.accounts.dto.ApiResponseDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.service.IAccountsService;
import com.example.accounts.util.ApiResponseBuilder;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST APIs for Accounts",
    description = "CREATE, FETCH, UPDATE and DELETE account details")
@Slf4j

//@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    private final IAccountsService iAccountsService;
    private final Environment environment;
    private final AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(IAccountsService iAccountsService, Environment environment, AccountsContactInfoDto accountsContactInfoDto) {
        this.iAccountsService = iAccountsService;
        this.environment = environment;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    @Value("${build.version}")
    private String buildVersion;


    @Operation(summary = "Create Account", description = "Create a new Customer and Account")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created"),
        @ApiResponse(responseCode = "409", description = "HTTP Status 409 Conflict", content=@Content(schema=@Schema(implementation=ApiResponseDto.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<Void>> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.CREATED, AccountsConstants.MESSAGE_201);
    }

    @Operation(summary = "Fetch Account details", description = "Fetch Customer and Account details based on the given mobile number")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
        @ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content=@Content(schema=@Schema(implementation=ApiResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content=@Content(schema=@Schema(implementation=ApiResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<ApiResponseDto<CustomerDto>> fetchAccountDetails(@RequestParam @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")                                                     String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponse(HttpStatus.OK, AccountsConstants.MESSAGE_200, customerDto);
    }

    @Operation(summary = "Update Account details", description = "Update Customer and Account details based on the given account number")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
        @ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content=@Content(schema=@Schema(implementation=ApiResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content=@Content(schema=@Schema(implementation=ApiResponseDto.class)))
    })
    @PostMapping("/update")
    public ResponseEntity<ApiResponseDto<Void>> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.updateAccount(customerDto);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK, AccountsConstants.MESSAGE_200);
    }

    @Operation(summary = "Delete Customer and Account details", description = "Delete Customer and Account details based on the given mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 Not Found", content=@Content(schema=@Schema(implementation=ApiResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto<Void>> deleteAccountDetails(@RequestParam @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")                                                         String mobileNumber) {
        iAccountsService.deleteAccount(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK, AccountsConstants.MESSAGE_200);
    }

    @GetMapping("/build-info")
    public ResponseEntity<ApiResponseDto<Void>> getBuildInfo(){
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK, buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<ApiResponseDto<Void>> getJavaVersion(){
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK, environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<ApiResponseDto<AccountsContactInfoDto>> getContactInfo(){
        return ApiResponseBuilder.buildSuccessResponse(HttpStatus.OK, "Contact Info", accountsContactInfoDto);
    }
}
