package org.jeycode.samples.infra.users.mapper;

import org.jeycode.samples.domain.users.models.User;
import org.jeycode.samples.infra.users.jpa_entities.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface UserEntityMapper {

  UserEntity toEntity(User user);

  User toDto(UserEntity userEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  UserEntity partialUpdate(User user,
      @MappingTarget UserEntity userEntity);
}