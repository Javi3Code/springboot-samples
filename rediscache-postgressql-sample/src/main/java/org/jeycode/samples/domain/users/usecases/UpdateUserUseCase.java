package org.jeycode.samples.domain.users.usecases;

import org.jeycode.samples.domain.users.dto.UpdatableUserDto;

public interface UpdateUserUseCase {

  void update(final UpdatableUserDto user);

}
