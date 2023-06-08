package org.jeycode.samples.domain.users.usecases;

import org.jeycode.samples.domain.users.models.User;

public interface GetUserByIdUseCase {

  User get(final long id);

}
