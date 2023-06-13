package org.jeycode.samples.application.users.mappers;

import java.util.List;
import org.jeycode.samples.domain.users.dtos.RegistrableUserDto;
import org.jeycode.samples.domain.users.dtos.UpdatableUserDto;
import org.jeycode.samples.domain.users.dtos.UserBasicInfoDto;
import org.jeycode.samples.domain.users.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface UsersMapper {

  List<UserBasicInfoDto> toDto(final List<User> users);

  User toUser(final RegistrableUserDto user, final String username);

  User toUser(long id, final String username, UpdatableUserDto user);
}
