package org.jeycode.samples.infra.books.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.infra.books.jpa_entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String>, JpaSpecificationExecutor<BookEntity> {


  @Modifying
  @Query("""
      UPDATE BookEntity b
      SET b.availableCopies = b.availableCopies + :availableCopies
      WHERE b.isbn = :isbn""")
  void updateAvailableCopies(@Param("isbn") String isbn, @Param("availableCopies") byte availableCopies);

  Optional<BookEntity> getByIsbn(String isbn);

  Optional<BookEntity> findFirstByTitleIgnoreCase(final String title);

  Set<String> findDistinctTitlesByAvailableCopiesGreaterThan(final int availableCopies);

  List<BookEntity> findByTitle(final String title);

  List<BookEntity> findByTitleStartsWithIgnoreCase(final String title);

  List<BookEntity> findByTitleAndAvailableCopiesGreaterThan(final String title, final int availableCopies);

  boolean existsByTitleAndAvailableCopiesGreaterThan(final String title, final int availableCopies);

  List<BookEntity> findByAuthor(final String author);

  List<BookEntity> findByAuthorAndTitleContaining(final String author, final String titleKeywords);

  @Query("select distinct b from BookEntity b")
  Set<BookGenre> findDistinctGenre();

  List<BookEntity> findByGenre(final BookGenre genre);

  List<BookEntity> findByOrdersStatus(final OrderStatus status);
}
