package org.jeycode.samples.domain.orders.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Order(
    long id,
    long userId,
    long bookId,
    OffsetDateTime rentalStartDate,
    OffsetDateTime rentalEndDate,
    BigDecimal totalPrice,
    OrderStatus status
) {

}

