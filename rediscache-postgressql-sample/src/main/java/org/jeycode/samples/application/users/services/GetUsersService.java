package org.jeycode.samples.application.users.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.users.dtos.UserBasicInfoDto;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.GetUsersUseCase;

@RequiredArgsConstructor
@UseCase
public class GetUsersService implements GetUsersUseCase {

  private final UsersMapper usersMapper;
  private final UserDataPort userDataPort;

  @Override
  public List<UserBasicInfoDto> get() {
    return usersMapper.toDto(userDataPort.getAll());
  }
}
