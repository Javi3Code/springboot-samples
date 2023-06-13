package org.jeycode.samples.domain.books.dtos;

import java.math.BigDecimal;

public record BookBasicInfoDto(
    String isbn,
    String author,
    byte availableCopies,
    BigDecimal pricePerDay
) {

}
