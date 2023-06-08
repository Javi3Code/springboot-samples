package org.jeycode.samples.infra.books.mapper;

import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.infra.books.jpa_entities.BookEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface BookEntityMapper {

  BookEntity toEntity(Book book);

  Book toDto(BookEntity bookEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  BookEntity partialUpdate(Book book,
      @MappingTarget BookEntity bookEntity);
}