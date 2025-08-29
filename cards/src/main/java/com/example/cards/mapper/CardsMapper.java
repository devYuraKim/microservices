package com.example.cards.mapper;

import com.example.cards.dto.CardsDto;
import com.example.cards.entity.Cards;

public class CardsMapper {

    private CardsMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CardsDto mapToCardsDto(Cards entity, CardsDto dto) {
        dto.setCardNumber(entity.getCardNumber());
        dto.setCardType(entity.getCardType());
        dto.setMobileNumber(entity.getMobileNumber());
        dto.setTotalLimit(entity.getTotalLimit());
        dto.setAvailableAmount(entity.getAvailableAmount());
        dto.setAmountUsed(entity.getAmountUsed());
        return dto;
    }

    public static Cards mapToCardsEntity(CardsDto dto, Cards entity) {
        entity.setCardNumber(entity.getCardNumber());
        entity.setCardType(entity.getCardType());
        entity.setMobileNumber(entity.getMobileNumber());
        entity.setTotalLimit(entity.getTotalLimit());
        entity.setAvailableAmount(entity.getAvailableAmount());
        entity.setAmountUsed(entity.getAmountUsed());
        return entity;
    }

}
