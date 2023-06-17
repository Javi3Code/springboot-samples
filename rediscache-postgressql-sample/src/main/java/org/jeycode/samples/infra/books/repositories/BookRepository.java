package org.jeycode.samples.infra.books.repositories;

import java.util.Optional;
import java.util.Set;
import org.jeycode.samples.domain.books.models.BookGenre;
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

  @Query("""
      select distinct b.title
      from BookEntity b
      where b.availableCopies > ?1""")
  Set<String> findDistinctTitlesByAvailableCopiesGreaterThan(final int availableCopies);

  boolean existsByTitleAndAvailableCopiesGreaterThan(final String title, final int availableCopies);


  @Query("""
      select distinct b.genre
      from BookEntity b""")
  Set<BookGenre> findDistinctGenre();

}
