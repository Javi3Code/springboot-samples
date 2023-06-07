package org.jeycode.samples.application.users.services;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.domain.users.dto.UpdatableUserDto;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.UpdateUserUseCase;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateUserService implements UpdateUserUseCase {

  private final UserDataPort userDataPort;
  private final UsersMapper usersMapper;

  @Override
  public void update(final UpdatableUserDto updatableUser) {
    final var id = updatableUser.id();
    if (!userDataPort.existsBy(id)) {
      throw new UserNotFoundException(format("User with id:[%s] doesn't exist", id));
    }
    userDataPort.update(usersMapper.toUser(updatableUser));
  }
}
