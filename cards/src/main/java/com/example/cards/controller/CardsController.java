package com.example.cards.controller;

import com.example.cards.constants.CardsConstants;
import com.example.cards.dto.ApiResponseDto;
import com.example.cards.dto.CardsDto;
import com.example.cards.service.ICardsService;
import com.example.cards.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Cards",
        description = "CREATE, FETCH, UPDATE and DELETE Card details")
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardsController {

    private final ICardsService iCardsService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<Void>> createCard(@Valid @RequestParam @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile number must be 11 digits") String mobileNumber) {
        iCardsService.createCard(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.CREATED, CardsConstants.MESSAGE_201);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponseDto<CardsDto>> fetchCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile number must be 11 digits") String mobileNumber) {
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponse(HttpStatus.OK, CardsConstants.MESSAGE_200, cardsDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto<Void>> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        iCardsService.updateCard(cardsDto);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK, CardsConstants.MESSAGE_200);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto<Void>> deleteCardDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile number must be 11 digits") String mobileNumber) {
        iCardsService.deleteCard(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK, CardsConstants.MESSAGE_200);
    }

}
