package org.jeycode.samples.domain.users.usecases;

import org.jeycode.samples.domain.users.dtos.UpdatableUserDto;

public interface UpdateUserUseCase {

  void update(final long id, final UpdatableUserDto user);

}
