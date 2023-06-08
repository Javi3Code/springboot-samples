package org.jeycode.samples.application.users.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.domain.users.dto.UserBasicInfoDto;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.GetUsersUseCase;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUsersService implements GetUsersUseCase {

  private final UsersMapper usersMapper;
  private final UserDataPort userDataPort;

  @Override
  public List<UserBasicInfoDto> get() {
    return usersMapper.toDto(userDataPort.getAll());
  }
}
