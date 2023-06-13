package org.jeycode.samples.infra.books.jpa_entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.jeycode.samples.infra.orders.jpa_entities.OrderEntity;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"orders"})
@Entity
@Table(indexes = {@Index(name = "idx_book_title", columnList = "title"), @Index(name = "idx_book_author", columnList = "author"),
    @Index(name = "idx_book_genre", columnList = "genre")})
public class BookEntity {

  @Id
  @EqualsAndHashCode.Include
  private String isbn;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String author;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BookGenre genre;

  @Column(nullable = false)
  private byte availableCopies;

  @Column(nullable = false)
  private BigDecimal pricePerDay;

  @Column(nullable = false)
  private Year publicationYear;

  @Column(nullable = false)
  private String description;

  @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Set<OrderEntity> orders = new HashSet<>();
}

