package org.jeycode.samples.application.users.services;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.components.UsernameGenerator;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.domain.users.dto.UserRegistrableDto;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.CreateUserUseCase;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUserService implements CreateUserUseCase {

  private final UserDataPort userDataPort;
  private final UsersMapper usersMapper;
  private final UsernameGenerator usernameGenerator;

  @Override
  public void create(final UserRegistrableDto userRegistrable) {
    final var username = usernameGenerator.generate(userRegistrable.firstName(), userRegistrable.lastName());
    userDataPort.register(usersMapper.toUser(userRegistrable, username));
  }
}
