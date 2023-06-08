package org.jeycode.samples.domain.users.usecases;

import org.jeycode.samples.domain.users.dto.RegistrableUserDto;

public interface CreateUserUseCase {

  void create(final RegistrableUserDto user);

}
