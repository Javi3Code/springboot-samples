package org.jeycode.samples.api_rest.options;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.domain.aaa_core.search.Books;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OptionsController {


  @GetMapping("api/v1/opt/books/genres")
  public ResponseEntity<BookGenre[]> getGenres() {
    return ResponseEntity.ok(BookGenre.values());
  }

  @GetMapping("api/v1/opt/search/books/fields")
  public ResponseEntity<Set<String>> getVisibleBookFields() {
    return ResponseEntity.ok(Books.SEARCH_CONSTRAINTS.getVisibleFields());
  }

}
