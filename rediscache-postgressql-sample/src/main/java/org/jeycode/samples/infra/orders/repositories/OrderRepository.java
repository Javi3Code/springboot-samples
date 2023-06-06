package org.jeycode.samples.infra.orders.repositories;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.jeycode.samples.domain.orders.models.OrderStatus;
import org.jeycode.samples.infra.orders.jpa_entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> , JpaSpecificationExecutor<OrderEntity> {

  List<OrderEntity> findByStatus(final OrderStatus status);

  BigDecimal getTotalPriceById(final long orderId);

  BigDecimal sumTotalPriceByStatus(final OrderStatus status);

  BigDecimal sumTotalPriceByStatusAndRentalStartDateBetween(final OrderStatus status, final OffsetDateTime startDateTime, OffsetDateTime endDateTime);

  void cancelOrderById(final long orderId);
}
