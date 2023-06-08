package org.jeycode.samples.domain.users.usecases;

import java.util.List;
import org.jeycode.samples.domain.users.dto.UserBasicInfoDto;

public interface GetUsersWithPendingOrdersUseCase {

  List<UserBasicInfoDto> get();
}
