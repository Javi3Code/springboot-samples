package org.jeycode.samples.domain.aaa_core.search.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record SearchCriteria(
    @NotNull @Size(min = 1)
    Set<@Valid SearchCriteriaFilter> filters,
    @NotNull @Size(min = 1)
    Set<String> fieldsToReturn,
    @Valid
    @NotNull
    DataPaginator dataPaginator

) {

}
