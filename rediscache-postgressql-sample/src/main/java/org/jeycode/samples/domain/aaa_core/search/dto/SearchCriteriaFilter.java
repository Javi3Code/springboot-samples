package org.jeycode.samples.domain.aaa_core.search.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.jeycode.samples.domain.aaa_core.search.enums.SearchSort;
import org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy;

@EqualsAndHashCode(of = "field", callSuper = false)
public record SearchCriteriaFilter(
    @NotBlank String field,
    @NotNull SearchStrategy strategy,
    @NotNull SearchSort sort
) {

}
