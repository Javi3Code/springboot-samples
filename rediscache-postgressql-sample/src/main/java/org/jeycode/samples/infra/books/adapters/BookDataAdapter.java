package org.jeycode.samples.infra.books.adapters;

import static org.jeycode.samples.domain.orders.models.OrderStatus.ACTIVE;
import static org.jeycode.samples.infra.utils.Caches.ALL_AVAILABLE_BOOKS;
import static org.jeycode.samples.infra.utils.Caches.BOOKS_CACHE;
import static org.jeycode.samples.infra.utils.Caches.BOOK_GENRES;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.infra.books.mapper.BookEntityMapper;
import org.jeycode.samples.infra.books.repositories.BookRepository;
import org.jeycode.samples.infra.utils.DatabaseAdapter;
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
    return bookRepository.findFirstByTitleIgnoreCase(title).map(bookEntityMapper::toDto);
  }


  @Cacheable(value = BOOKS_CACHE, key = ALL_AVAILABLE_BOOKS, unless = "#result == null")
  @Override
  public Set<String> getTitleOfAvailableCopies() {
    logger.info("getTitleOfBooksWithAvailableCopies -- db action");
    return bookRepository.findDistinctTitlesByAvailableCopiesGreaterThan(0);
  }

  @Cacheable(value = BOOKS_CACHE, key = "#title", unless = "#result == null")
  @Override
  public List<Book> getByTitle(final String title) {
    logger.info("getBooksWithThis -- db action -- title: [{}]", title);
    return bookRepository.findByTitle(title).stream()
        .map(bookEntityMapper::toDto)
        .toList();
  }

  @Override
  public List<Book> getByTitleStartingWith(final String title) {
    logger.info("getBooksByTitleStartingWith -- db action -- title: [{}]", title);
    return bookRepository.findByTitleStartsWithIgnoreCase(title).stream()
        .map(bookEntityMapper::toDto)
        .toList();
  }

  @Override
  public List<Book> getAvailableCopiesByTitle(final String title) {
    logger.info("getBooksWithAvailableCopiesByTitle -- db action -- title: [{}]", title);
    return bookRepository.findByTitleAndAvailableCopiesGreaterThan(title, 0).stream()
        .map(bookEntityMapper::toDto)
        .toList();
  }

  @Override
  public boolean thereAreCopiesOf(final String title) {
    logger.info("thereAreBooksWithCopiesOf -- db action -- title: [{}]", title);
    return bookRepository.existsByTitleAndAvailableCopiesGreaterThan(title, 0);
  }

  @Override
  public List<Book> getByAuthor(final String author) {
    logger.info("getBooksByAuthor -- db action -- author: [{}]", author);
    return bookRepository.findByAuthor(author).stream()
        .map(bookEntityMapper::toDto)
        .toList();
  }

  @Override
  public List<Book> getByAuthorAndTitleContaining(final String author, final String titleKeywords) {
    logger.info("""
        getBooksByAuthorAndTitleContaining -- db action --
              author: [{}]
              titleKeywords: [{}]""", author, titleKeywords);
    return bookRepository.findByAuthorAndTitleContaining(author, titleKeywords).stream()
        .map(bookEntityMapper::toDto)
        .toList();
  }

  @Cacheable(value = BOOKS_CACHE, key = BOOK_GENRES)
  @Override
  public Set<BookGenre> getGenres() {
    logger.info("getBookGenres -- db action");
    return bookRepository.findDistinctGenre();
  }

  @Override
  public List<Book> getByGenre(final BookGenre genre) {
    logger.info("getBooksByGenre -- db action -- genre: [{}]", genre);
    return bookRepository.findByGenre(genre).stream()
        .map(bookEntityMapper::toDto)
        .toList();
  }

  @Override
  public List<Book> getRentedBooks() {
    logger.info("getRentedBooks -- db action");
    return bookRepository.findByOrdersStatus(ACTIVE).stream()
        .map(bookEntityMapper::toDto)
        .toList();
  }

  @Caching(
      evict = {
          @CacheEvict(value = BOOKS_CACHE, key = "#book.isbn()"),
          @CacheEvict(value = BOOKS_CACHE, key = "#book.title()"),
          @CacheEvict(value = BOOKS_CACHE, key = ALL_AVAILABLE_BOOKS),
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
