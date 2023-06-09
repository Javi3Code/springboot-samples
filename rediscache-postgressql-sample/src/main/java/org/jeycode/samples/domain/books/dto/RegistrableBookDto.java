package org.jeycode.samples.domain.books.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Year;
import org.jeycode.samples.domain.books.models.BookGenre;

public record RegistrableBookDto(@NotBlank String title,
                                 @NotBlank String author,
                                 @NotNull BookGenre genre,
                                 @Positive byte availableCopies,
                                 @Positive BigDecimal pricePerDay,
                                 @NotNull Year publicationYear,
                                 @NotNull String description) {

}
