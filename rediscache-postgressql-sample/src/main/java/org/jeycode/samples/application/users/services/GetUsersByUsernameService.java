package org.jeycode.samples.application.users.services;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.domain.users.dto.UserBasicInfoDto;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.GetUsersByUsernameUseCase;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUsersByUsernameService implements GetUsersByUsernameUseCase {

  private final UserDataPort userDataPort;
  private final UsersMapper usersMapper;

  @Override
  public List<UserBasicInfoDto> getAllBeginsWith(final String username) {
    return Optional.ofNullable(userDataPort.getAllByUsernameBeginsWith(username))
        .map(usersMapper::toDto)
        .orElseThrow(() -> new UserNotFoundException(format("there are no users whose username begins with: [%s]", username)));
  }
}
