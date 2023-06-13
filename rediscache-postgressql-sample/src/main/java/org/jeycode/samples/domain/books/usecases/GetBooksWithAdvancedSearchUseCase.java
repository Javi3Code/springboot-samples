package org.jeycode.samples.domain.books.usecases;

import java.util.List;
import java.util.Map;
import org.jeycode.samples.domain.aaa_core.search.dto.DataPage;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;

public interface GetBooksWithAdvancedSearchUseCase {

  DataPage<List<Map<String, Object>>> getBy(final SearchCriteria bookSearchCriteria);
}
