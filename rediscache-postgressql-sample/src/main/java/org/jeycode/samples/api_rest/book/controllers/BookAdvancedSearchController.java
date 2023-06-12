package org.jeycode.samples.api_rest.book.controllers;

import static org.jeycode.samples.domain.aaa_core.search.dto.ObjectType.BOOKS;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.api_rest.aaa_core.exceptions_handler.annotations.SearchFields;
import org.jeycode.samples.domain.aaa_core.search.dto.DataPage;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;
import org.jeycode.samples.domain.books.models.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookAdvancedSearchController {


  @GetMapping("api/v1/books")
  public ResponseEntity<DataPage<Book>> getAllBy(
      @RequestBody(required = true) @Valid @SearchFields(of = BOOKS) final SearchCriteria searchCriteria) {

    return ResponseEntity.ok(null);
  }


}
