package org.jeycode.samples.domain.orders.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record Order(
    long id,
    long userId,
    List<Long> bookIds,
    OffsetDateTime rentalStartDate,
    OffsetDateTime rentalEndDate,
    BigDecimal totalPrice,
    OrderStatus status
) {

}

