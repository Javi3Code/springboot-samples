package org.jeycode.samples.infra.orders.jpa_entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.infra.books.jpa_entities.BookEntity;
import org.jeycode.samples.infra.users.jpa_entities.UserEntity;

@Data
@ToString(exclude = {"user", "books"})
@Entity
@Table(indexes = @Index(name = "idx_order_status", columnList = "status"))
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, updatable = false)
  private OffsetDateTime rentalStartDate;

  @Column(nullable = true)
  private OffsetDateTime rentalEndDate;

  @Column(nullable = true)
  private BigDecimal totalPrice;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    final OrderEntity that = (OrderEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private UserEntity user;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinTable(name = "order_book",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id"))
  private Set<BookEntity> books = new HashSet<>();

  public void addUser(final UserEntity user) {
    user.getOrders().add(this);
    this.setUser(user);
  }

  public void addBook(final BookEntity book) {
    books.add(book);
    book.getOrders().add(this);
  }

  public void addBooks(final List<BookEntity> books) {
    this.books.addAll(books);
    books.forEach(book -> book.getOrders().add(this));
  }

}