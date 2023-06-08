package org.jeycode.samples.domain.books.dto;

import jakarta.validation.constraints.Positive;

public record BookCopiesWrapper(@Positive byte copies) {

}
