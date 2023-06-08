package org.jeycode.samples.application.users.services;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.domain.users.dto.UpdatableUserDto;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.jeycode.samples.domain.users.models.User;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.UpdateUserUseCase;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PartialUpdateUserService implements UpdateUserUseCase {

  private final UserDataPort userDataPort;
  private final UsersMapper usersMapper;

  @Override
  public void update(final long id, final UpdatableUserDto user) {
    final var actualUser = userDataPort.getBy(id);
    if (isNull(actualUser)) {
      throw new UserNotFoundException(format("User with id:[%s] doesn't exist", id));
    }
    if (this.haveDiffFields(user, actualUser)) {
      userDataPort.update(usersMapper.toUser(id, actualUser.username(), user));
    }
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
