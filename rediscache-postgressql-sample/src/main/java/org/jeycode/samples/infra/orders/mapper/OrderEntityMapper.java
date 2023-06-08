package org.jeycode.samples.infra.orders.mapper;

import org.jeycode.samples.domain.orders.models.Order;
import org.jeycode.samples.infra.orders.jpa_entities.OrderEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface OrderEntityMapper {

  OrderEntity toEntity(Order order);

  Order toDto(OrderEntity orderEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  OrderEntity partialUpdate(Order order,
      @MappingTarget OrderEntity orderEntity);
}