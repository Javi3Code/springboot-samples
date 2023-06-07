package org.jeycode.samples.domain.users.usecases;

import org.jeycode.samples.domain.users.dto.UserRegistrableDto;

public interface CreateUserUseCase {

  void create(final UserRegistrableDto user);

}
