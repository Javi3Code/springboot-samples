package org.jeycode.samples.domain.aaa_core.search.dto;

import jakarta.validation.constraints.Min;

public record DataPaginator(@Min(0) int page, @Min(1) int size) {

}
