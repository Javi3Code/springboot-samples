package org.jeycode.samples.infra.books.mapper;

import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.infra.books.jpa_entities.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface BookEntityMapper {

  BookEntity toEntity(Book book);

  Book toDto(BookEntity bookEntity);

}