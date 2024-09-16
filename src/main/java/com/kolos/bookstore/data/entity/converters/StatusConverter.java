package com.kolos.bookstore.data.entity.converters;

import com.kolos.bookstore.data.entity.Order;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Order.Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Order.Status status) {
        if (status == null) {
            return null;
        }

        return switch (status) {
            case PENDING -> 1;
            case PAID -> 2;
            case DELIVERED -> 3;
            case CANCELLED -> 4;
        };
    }

    @Override
    public Order.Status convertToEntityAttribute(Integer integer) {

        if (integer == null) {
            return null;
        }


        return switch (integer) {
            case 1 -> Order.Status.PENDING;
            case 2 -> Order.Status.PAID;
            case 3 -> Order.Status.DELIVERED;
            case 4 -> Order.Status.CANCELLED;
            default -> throw new IllegalStateException("Invalid status id " + integer);
        };
    }
}
