package org.jeycode.samples.infra.orders.repositories;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.infra.orders.jpa_entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {

  List<OrderEntity> findByStatus(final OrderStatus status);

  BigDecimal getTotalPriceById(final long orderId);

  @Query("""
      SELECT SUM(o.totalPrice)
      FROM OrderEntity o
      WHERE o.status = :status""")
  BigDecimal sumTotalPriceByStatus(@Param("status") OrderStatus status);

  @Query("""
      SELECT SUM(o.totalPrice)
      FROM OrderEntity o
      WHERE o.status = :status AND o.rentalStartDate BETWEEN :startDateTime AND :endDateTime""")
  BigDecimal sumTotalPriceByStatusAndRentalStartDateBetween(@Param("status") OrderStatus status,
      @Param("startDateTime") OffsetDateTime startDateTime, @Param("endDateTime") OffsetDateTime endDateTime);

  @Modifying
  @Query("""
      UPDATE OrderEntity o
      SET o.status = 'Cancelled'
      WHERE o.id = :orderId""")
  void cancelOrderById(long orderId);
}
