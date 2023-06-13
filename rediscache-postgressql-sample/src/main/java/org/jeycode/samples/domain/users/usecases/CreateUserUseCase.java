package org.jeycode.samples.domain.users.usecases;

import org.jeycode.samples.domain.users.dtos.RegistrableUserDto;

public interface CreateUserUseCase {

  void create(final RegistrableUserDto user);

}
