package org.jeycode.samples.infra.aaa_core.components;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.jeycode.samples.domain.aaa_core.search.dto.DataPaginator;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteriaFilter;
import org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

public final class SearchCriteriaTransformer<T, R> {

  public Specification<T> createSpecWith(final Set<SearchCriteriaFilter> filters) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>(filters.size());

      for (SearchCriteriaFilter filter : filters) {
        String field = filter.field();
        SearchStrategy strategy = filter.strategy();
        String value = filter.value();

        switch (strategy) {
          case STARTS_WITH -> predicates.add(criteriaBuilder.like(root.get(field), value + "%"));
          case CONTAINS -> predicates.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
          case END_WITH -> predicates.add(criteriaBuilder.like(root.get(field), "%" + value));
          case GREATER_THAN -> predicates.add(criteriaBuilder.greaterThan(root.get(field), value));
          case EQUAL_TO -> predicates.add(criteriaBuilder.equal(root.get(field), value));
          case LESS_THAN -> predicates.add(criteriaBuilder.lessThan(root.get(field), value));
          default -> {
          }
        }
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public Function<FetchableFluentQuery<T>, Page<R>> createQueryWith(
      final Set<String> requiredFields, final DataPaginator paginator) {
  }
}
