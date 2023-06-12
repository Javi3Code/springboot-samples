package org.jeycode.samples.domain.books.usecases;

import org.jeycode.samples.domain.books.dtos.BookBasicInfoDto;

public interface GetBookByTitleUseCase {

  BookBasicInfoDto get(final String title);

}
