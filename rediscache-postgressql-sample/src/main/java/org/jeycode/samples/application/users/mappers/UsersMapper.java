package org.jeycode.samples.application.users.mappers;

import java.util.List;
import org.jeycode.samples.domain.users.dto.UpdatableUserDto;
import org.jeycode.samples.domain.users.dto.UserBasicInfoDto;
import org.jeycode.samples.domain.users.dto.UserRegistrableDto;
import org.jeycode.samples.domain.users.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface UsersMapper {

  List<UserBasicInfoDto> toDto(final List<User> users);

  User toUser(final UpdatableUserDto updatableUser);

  User toUser(final UserRegistrableDto userRegistrable, final String username);

}
