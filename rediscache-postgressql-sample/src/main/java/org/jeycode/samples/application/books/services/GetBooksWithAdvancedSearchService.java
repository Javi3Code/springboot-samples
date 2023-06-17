package org.jeycode.samples.application.books.services;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.domain.aaa_core.search.dto.DataPage;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.domain.books.usecases.GetBooksWithAdvancedSearchUseCase;
import org.jeycode.samples.infra.aaa_core.annotations.DatabaseAdapter;

@RequiredArgsConstructor
@DatabaseAdapter
public class GetBooksWithAdvancedSearchService implements GetBooksWithAdvancedSearchUseCase {

  private final BookDataPort bookDataPort;

  @Override
  public DataPage<List<Map<String, Object>>> getBy(final SearchCriteria searchCriteria) {
    return bookDataPort.getAllBy(searchCriteria);
  }
}
