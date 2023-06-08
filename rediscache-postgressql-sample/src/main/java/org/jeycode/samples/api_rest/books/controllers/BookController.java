package org.jeycode.samples.api_rest.books.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.domain.books.dto.BookCopiesWrapper;
import org.jeycode.samples.domain.books.dto.RegistrableBookDto;
import org.jeycode.samples.domain.books.usecases.RegisterBookUseCase;
import org.jeycode.samples.domain.books.usecases.RegisterCopiesOfBookUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {

  private final RegisterCopiesOfBookUseCase registerCopiesOfBookUseCase;
  private final RegisterBookUseCase registerBookUseCase;

  @PutMapping("api/v1/books/{isbn}")
  public ResponseEntity<Void> addCopies(@PathVariable(value = "isbn", required = true) @Valid @NotBlank final String isbn,
      @RequestBody(required = true) @Valid final BookCopiesWrapper copiesWrapper) {
    registerCopiesOfBookUseCase.add(isbn, copiesWrapper.copies());
    return ResponseEntity.noContent().build();
  }

  @PostMapping("api/v1/books/{isbn}")
  public ResponseEntity<Void> addCopies(@PathVariable(value = "isbn", required = true) @Valid @NotBlank final String isbn,
      @RequestBody(required = true) @Valid final RegistrableBookDto book) {
    registerBookUseCase.register(isbn, book);
    return ResponseEntity.status(CREATED).build();
  }


}
