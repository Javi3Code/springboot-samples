package org.jeycode.samples.domain.aaa_core.search.dto;

import java.util.EnumMap;
import java.util.Set;
import org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy;

public record SearchConstraints(
    Set<String> visibleFields,
    Set<String> filterFields,
    EnumMap<SearchStrategy, Set<String>> searchStrategy
) {

}
