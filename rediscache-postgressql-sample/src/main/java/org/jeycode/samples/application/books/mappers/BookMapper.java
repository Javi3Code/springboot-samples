package org.jeycode.samples.application.books.mappers;

import org.jeycode.samples.domain.books.dto.RegistrableBookDto;
import org.jeycode.samples.domain.books.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface BookMapper {

  @Mapping(target = "availableCopies", source = "availableCopies")
  Book toBook(final Book book, final byte availableCopies);

  Book toBook(final String isbn, final RegistrableBookDto registrableBook);
}
