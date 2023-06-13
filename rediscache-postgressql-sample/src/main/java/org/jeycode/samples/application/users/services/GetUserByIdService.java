package org.jeycode.samples.application.users.services;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.jeycode.samples.domain.users.models.User;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.GetUserByIdUseCase;

@RequiredArgsConstructor
@UseCase
public class GetUserByIdService implements GetUserByIdUseCase {

  private final UserDataPort userDataPort;

  @Override
  public User get(final long id) {
    return userDataPort.getBy(id)
        .orElseThrow(() -> new UserNotFoundException(format("User with id:[%s] doesn't exist", id)));
  }
}
