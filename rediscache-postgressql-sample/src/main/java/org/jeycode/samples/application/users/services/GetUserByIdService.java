package org.jeycode.samples.application.users.services;

import static java.lang.String.format;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.jeycode.samples.domain.users.models.User;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.GetUserByIdUseCase;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUserByIdService implements GetUserByIdUseCase {

  private final UserDataPort userDataPort;

  @Override
  public User get(final long id) {
    return Optional.ofNullable(userDataPort.getBy(id))
        .orElseThrow(() -> new UserNotFoundException(format("User with id:[%s] doesn't exist", id)));
  }
}
