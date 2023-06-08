package org.jeycode.samples.infra.books.repositories;

import java.util.List;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.infra.books.jpa_entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String>, JpaSpecificationExecutor<BookEntity> {

  List<BookEntity> findByTitle(final String title);

  List<BookEntity> findByTitleOrIsbnAndAvailableCopiesGreaterThan(final String title, final String isbn, final byte availableCopies);

  List<BookEntity> findByTitleOrIsbnAndAvailableCopiesIs(final String title, final String isbn, final byte availableCopies);

  boolean existsByIsbnOrTitleAndAvailableCopiesGreaterThan(final String isbn, final String title, final byte availableCopies);

  List<BookEntity> findByAuthor(final String author);

  List<BookEntity> findByTitleContaining(final String keyword);

  List<BookEntity> findByGenre(final BookGenre genre);

  List<BookEntity> findByOrdersStatus(final OrderStatus status);
}
