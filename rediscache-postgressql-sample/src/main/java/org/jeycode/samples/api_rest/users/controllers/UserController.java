package org.jeycode.samples.api_rest.users.controllers;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.domain.users.dto.UserBasicInfoDto;
import org.jeycode.samples.domain.users.models.User;
import org.jeycode.samples.domain.users.usecases.GetUserByIdUseCase;
import org.jeycode.samples.domain.users.usecases.GetUsersByUsernameUseCase;
import org.jeycode.samples.domain.users.usecases.GetUsersUseCase;
import org.jeycode.samples.domain.users.usecases.GetUsersWithPendingOrdersUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final GetUserByIdUseCase getUserByIdUseCase;
  private final GetUsersUseCase getUsersUseCase;
  private final GetUsersByUsernameUseCase getUsersByUsernameUseCase;
  private final GetUsersWithPendingOrdersUseCase getUsersWithPendingOrdersUseCase;


  @GetMapping("api/v1/users/{id}")
  public ResponseEntity<User> getOneBy(@PathVariable(value = "id", required = true) final long id) {
    return ResponseEntity.ok(getUserByIdUseCase.get(id));
  }

  @GetMapping("api/v1/users")
  public ResponseEntity<List<UserBasicInfoDto>> getAll() {
    return ResponseEntity.ok(getUsersUseCase.get());
  }

  @GetMapping(value = "api/v1/users", params = "username")
  public ResponseEntity<List<UserBasicInfoDto>> getAllByUsername(@RequestParam("username") @Size(min = 4) final String username) {
    return ResponseEntity.ok(getUsersByUsernameUseCase.getAllBeginsWith(username));
  }

  @GetMapping("api/v1/pending-orders/users")
  public ResponseEntity<List<UserBasicInfoDto>> getUsersWithPendingOrders() {
    return ResponseEntity.ok(getUsersWithPendingOrdersUseCase.get());
  }


}
