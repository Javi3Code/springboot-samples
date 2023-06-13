package org.jeycode.samples.domain.books.dtos;

import jakarta.validation.constraints.Positive;

public record BookCopiesWrapper(@Positive byte copies) {

}
