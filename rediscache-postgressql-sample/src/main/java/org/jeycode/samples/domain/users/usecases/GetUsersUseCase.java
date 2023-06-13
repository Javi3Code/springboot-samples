package org.jeycode.samples.domain.users.usecases;

import java.util.List;
import org.jeycode.samples.domain.users.dtos.UserBasicInfoDto;

public interface GetUsersUseCase {

  List<UserBasicInfoDto> get();

}
