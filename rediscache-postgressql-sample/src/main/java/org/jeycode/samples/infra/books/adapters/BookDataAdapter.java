package org.jeycode.samples.infra.books.adapters;

import static org.jeycode.samples.infra.aaa_core.utils.Caches.ALL_AVAILABLE_BOOKS;
import static org.jeycode.samples.infra.aaa_core.utils.Caches.BOOKS_CACHE;
import static org.jeycode.samples.infra.aaa_core.utils.Caches.BOOK_GENRES;

import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.infra.aaa_core.annotations.DatabaseAdapter;
import org.jeycode.samples.infra.books.mapper.BookEntityMapper;
import org.jeycode.samples.infra.books.repositories.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@DatabaseAdapter
public class BookDataAdapter implements BookDataPort {

  private final BookEntityMapper bookEntityMapper;
  private final BookRepository bookRepository;

  @Cacheable(value = BOOKS_CACHE, key = "#isbn", unless = "#result == null")
  @Override
  public Optional<Book> getBy(final String isbn) {
    logger.info("getBookById -- db action -- isbn: [{}]", isbn);
    return bookRepository.getByIsbn(isbn).map(bookEntityMapper::toDto);
  }

  @Override
  public Optional<Book> getOnePossibleCopyWithThisTitle(final String title) {
    logger.info("getOnePossibleBookCopyWithThisTitle -- db action -- title: [{}]", title);
    return bookRepository.findFirstByTitleIgnoreCase(title)
        .map(bookEntityMapper::toDto);
  }

  @Cacheable(value = BOOKS_CACHE, key = ALL_AVAILABLE_BOOKS, unless = "#result == null or #result.empty")
  @Override
  public Set<String> getTitleOfAvailableCopies() {
    logger.info("getTitleOfBooksWithAvailableCopies -- db action");
    return bookRepository.findDistinctTitlesByAvailableCopiesGreaterThan(0);
  }

  @Override
  public boolean thereAreCopiesOf(final String title) {
    logger.info("thereAreBooksWithCopiesOf -- db action -- title: [{}]", title);
    return bookRepository.existsByTitleAndAvailableCopiesGreaterThan(title, 0);
  }

  @Cacheable(value = BOOKS_CACHE, key = BOOK_GENRES)
  @Override
  public Set<BookGenre> getGenres() {
    logger.info("getBookGenres -- db action");
    return bookRepository.findDistinctGenre();
  }

  @Caching(
      evict = {
          @CacheEvict(value = BOOKS_CACHE, key = "#book.isbn()"),
          @CacheEvict(value = BOOKS_CACHE, key = "#book.title()"),
          @CacheEvict(value = BOOKS_CACHE, key = BOOK_GENRES),
          @CacheEvict(value = BOOKS_CACHE, key = "#book.genre()")
      })
  @Transactional
  @Override
  public void addCopiesOf(final Book book) {
    logger.info("""
        addCopiesOfBook -- db action -- book:
               {}""", book);
    bookRepository.updateAvailableCopies(book.isbn(), book.availableCopies());
  }

  @Caching(
      evict = {
          @CacheEvict(value = BOOKS_CACHE, key = "#book.title()"),
          @CacheEvict(value = BOOKS_CACHE, key = BOOK_GENRES),
          @CacheEvict(value = BOOKS_CACHE, key = ALL_AVAILABLE_BOOKS),
          @CacheEvict(value = BOOKS_CACHE, key = "#book.genre()")
      }
  )
  @Transactional
  @Override
  public void register(final Book book) {
    logger.info("""
        registerBook -- db action -- book:
               {}""", book);
    bookRepository.save(bookEntityMapper.toEntity(book));
  }

}
