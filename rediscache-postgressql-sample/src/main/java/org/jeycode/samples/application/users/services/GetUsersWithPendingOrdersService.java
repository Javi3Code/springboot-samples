package org.jeycode.samples.application.users.services;


import static org.jeycode.samples.domain.orders.models.OrderStatus.ACTIVE;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.users.mappers.UsersMapper;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.users.dtos.UserBasicInfoDto;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.jeycode.samples.domain.users.usecases.GetUsersWithPendingOrdersUseCase;

@RequiredArgsConstructor
@UseCase
public class GetUsersWithPendingOrdersService implements GetUsersWithPendingOrdersUseCase {

  private final UsersMapper usersMapper;
  private final UserDataPort userDataPort;

  @Override
  public List<UserBasicInfoDto> get() {
    return Optional.ofNullable(userDataPort.getAllByOrderStatus(ACTIVE))
        .map(usersMapper::toDto)
        .orElseThrow(() -> new UserNotFoundException("No users with pending orders"));
  }
}
