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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.infra.books.jpa_entities.BookEntity;
import org.jeycode.samples.infra.users.jpa_entities.UserEntity;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"user", "book"})
@Entity
@Table(indexes = @Index(name = "idx_order_status", columnList = "status"))
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private long id;

  @Column(nullable = false, updatable = false)
  private OffsetDateTime rentalStartDate;

  @Column(nullable = true)
  private OffsetDateTime rentalEndDate;

  @Column(nullable = true)
  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "bookId")
  private BookEntity book;

}