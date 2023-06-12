package org.jeycode.samples.application.users.services;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.components.UsernameGenerator;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.users.dtos.RegistrableUserDto;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.CreateUserUseCase;

@RequiredArgsConstructor
@UseCase
public class CreateUserService implements CreateUserUseCase {

  private final UsersMapper usersMapper;
  private final UserDataPort userDataPort;
  private final UsernameGenerator usernameGenerator;

  @Override
  public void create(final RegistrableUserDto user) {
    final var username = usernameGenerator.generate(user.firstName(), user.lastName());
    userDataPort.register(usersMapper.toUser(user, username));
  }
}
