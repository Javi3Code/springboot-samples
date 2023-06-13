package org.jeycode.samples.application.users.services;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.users.dtos.UpdatableUserDto;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.jeycode.samples.domain.users.models.User;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.UpdateUserUseCase;

@RequiredArgsConstructor
@UseCase
public class PartialUpdateUserService implements UpdateUserUseCase {

  private final UsersMapper usersMapper;
  private final UserDataPort userDataPort;

  @Override
  public void update(final long id, final UpdatableUserDto user) {
    userDataPort.getBy(id)
        .ifPresentOrElse(actualUser -> {
          if (this.haveDiffFields(user, actualUser)) {
            userDataPort.update(usersMapper.toUser(id, actualUser.username(), user));
          }
        }, () -> {
          throw new UserNotFoundException(format("User with id:[%s] doesn't exist", id));
        });
  }

  private boolean haveDiffFields(final UpdatableUserDto user, final User actualUser) {
    final var firstName = user.firstName();
    if (isNotBlank(firstName) && firstName.equals(actualUser.firstName())) {
      return true;
    }
    final var lastName = user.lastName();
    if (isNotBlank(lastName) && lastName.equals(actualUser.lastName())) {
      return true;
    }
    final var email = user.email();
    if (isNotBlank(email) && email.equals(actualUser.email())) {
      return true;
    }
    final var address = user.address();
    if (isNotBlank(address) && address.equals(actualUser.address())) {
      return true;
    }
    final var phoneNumber = user.phoneNumber();
    return isNotBlank(phoneNumber) && phoneNumber.equals(actualUser.phoneNumber());
  }
}
