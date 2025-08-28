package com.example.cards.service;

import com.example.cards.dto.CardsDto;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    void updateCard(CardsDto cardsDto);

    void deleteCard(String mobileNumber);


}
