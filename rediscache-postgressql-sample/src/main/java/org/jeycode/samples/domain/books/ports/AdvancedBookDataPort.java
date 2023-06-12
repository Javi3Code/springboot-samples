package org.jeycode.samples.domain.books.ports;

import java.util.List;
import org.jeycode.samples.domain.aaa_core.search.dto.DataPage;
import org.jeycode.samples.domain.aaa_core.search.dto.DataPager;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;
import org.jeycode.samples.domain.books.models.Book;

public interface AdvancedBookDataPort {

  DataPage<List<Book>> getBy(final SearchCriteria bookSearchCriteria, DataPager pager);

}
