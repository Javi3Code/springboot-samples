package org.jeycode.samples.api_rest.book.controllers;

import static org.jeycode.samples.domain.aaa_core.search.dto.ObjectType.BOOKS;
import static org.springframework.http.HttpStatus.CREATED;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.api_rest.aaa_core.exceptions_handler.annotations.SearchFields;
import org.jeycode.samples.domain.aaa_core.search.dto.DataPage;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;
import org.jeycode.samples.domain.books.dtos.BookBasicInfoDto;
import org.jeycode.samples.domain.books.dtos.BookCopiesWrapper;
import org.jeycode.samples.domain.books.dtos.RegistrableBookDto;
import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.jeycode.samples.domain.books.usecases.GetAvailableBookTitlesUseCase;
import org.jeycode.samples.domain.books.usecases.GetBookByIsbnUseCase;
import org.jeycode.samples.domain.books.usecases.GetBookByTitleUseCase;
import org.jeycode.samples.domain.books.usecases.GetBooksGenresUseCase;
import org.jeycode.samples.domain.books.usecases.GetBooksWithAdvancedSearchUseCase;
import org.jeycode.samples.domain.books.usecases.RegisterBookUseCase;
import org.jeycode.samples.domain.books.usecases.RegisterCopiesOfBookUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {

  private final GetBookByIsbnUseCase getBookByIsbnUseCase;
  private final GetBooksWithAdvancedSearchUseCase getBooksWithAdvancedSearchUseCase;
  private final GetBookByTitleUseCase getBookByTitleUseCase;
  private final GetAvailableBookTitlesUseCase getAvailableBookTitlesUseCase;
  private final GetBooksGenresUseCase getBookGenresUseCase;
  private final RegisterCopiesOfBookUseCase registerCopiesOfBookUseCase;
  private final RegisterBookUseCase registerBookUseCase;

  @GetMapping("api/v1/books/{isbn}")
  public ResponseEntity<Book> get(@PathVariable(value = "isbn", required = true) @Valid @NotBlank final String isbn) {
    return ResponseEntity.ok(getBookByIsbnUseCase.get(isbn));
  }

  @GetMapping("api/v1/books")
  public ResponseEntity<DataPage<List<Map<String, Object>>>> getAllBy(
      @RequestBody(required = true) @Valid @SearchFields(of = BOOKS) final SearchCriteria searchCriteria) {
    return ResponseEntity.ok(getBooksWithAdvancedSearchUseCase.getBy(searchCriteria));
  }

  @GetMapping("api/v1/book")
  public ResponseEntity<BookBasicInfoDto> getCopy(@RequestParam(value = "title", required = true) final String title) {
    return ResponseEntity.ok(getBookByTitleUseCase.get(title));
  }

  @GetMapping("api/v1/books/titles")
  public ResponseEntity<Set<String>> getTitles() {
    return ResponseEntity.ok(getAvailableBookTitlesUseCase.get());
  }

  @GetMapping("api/v1/books/genres")
  public ResponseEntity<Set<BookGenre>> getGenres() {
    return ResponseEntity.ok(getBookGenresUseCase.get());
  }

  @PutMapping("api/v1/books/{isbn}")
  public ResponseEntity<Void> add(@PathVariable(value = "isbn", required = true) @Valid @NotBlank final String isbn,
      @RequestBody(required = true) @Valid final BookCopiesWrapper copiesWrapper) {
    registerCopiesOfBookUseCase.add(isbn, copiesWrapper.copies());
    return ResponseEntity.noContent().build();
  }

  @PostMapping("api/v1/books/{isbn}")
  public ResponseEntity<Void> register(@PathVariable(value = "isbn", required = true) @Valid @NotBlank final String isbn,
      @RequestBody(required = true) @Valid final RegistrableBookDto book) {
    registerBookUseCase.register(isbn, book);
    return ResponseEntity.status(CREATED).build();
  }


}
