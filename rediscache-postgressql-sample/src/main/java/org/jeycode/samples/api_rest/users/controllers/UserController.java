package org.jeycode.samples.api_rest.users.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.domain.users.dto.RegistrableUserDto;
import org.jeycode.samples.domain.users.dto.UpdatableUserDto;
import org.jeycode.samples.domain.users.dto.UserBasicInfoDto;
import org.jeycode.samples.domain.users.models.User;
import org.jeycode.samples.domain.users.usecases.CreateUserUseCase;
import org.jeycode.samples.domain.users.usecases.GetUserByIdUseCase;
import org.jeycode.samples.domain.users.usecases.GetUsersByUsernameUseCase;
import org.jeycode.samples.domain.users.usecases.GetUsersUseCase;
import org.jeycode.samples.domain.users.usecases.GetUsersWithPendingOrdersUseCase;
import org.jeycode.samples.domain.users.usecases.UpdateUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final GetUserByIdUseCase getUserByIdUseCase;
  private final GetUsersUseCase getUsersUseCase;
  private final GetUsersByUsernameUseCase getUsersByUsernameUseCase;
  private final GetUsersWithPendingOrdersUseCase getUsersWithPendingOrdersUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final UpdateUserUseCase updateUserUseCase;


  @GetMapping("api/v1/users/{id}")
  public ResponseEntity<User> getOneBy(@PathVariable(value = "id", required = true) @Valid @Positive final long id) {
    return ResponseEntity.ok(getUserByIdUseCase.get(id));
  }

  @GetMapping("api/v1/users")
  public ResponseEntity<List<UserBasicInfoDto>> getAll() {
    return ResponseEntity.ok(getUsersUseCase.get());
  }

  @GetMapping(value = "api/v1/users", params = "username")
  public ResponseEntity<List<UserBasicInfoDto>> getAllByUsername(@RequestParam("username") @Size(min = 4) final String username) {
    return ResponseEntity.ok(getUsersByUsernameUseCase.getStartingWith(username));
  }

  @GetMapping("api/v1/pending-orders/users")
  public ResponseEntity<List<UserBasicInfoDto>> getUsersWithPendingOrders() {
    return ResponseEntity.ok(getUsersWithPendingOrdersUseCase.get());
  }

  @PostMapping("api/v1/users")
  public ResponseEntity<Void> createUser(@RequestBody(required = true) @Valid final RegistrableUserDto user) {
    createUserUseCase.create(user);
    return ResponseEntity.status(CREATED).build();
  }

  @PatchMapping("api/v1/users/{id}")
  public ResponseEntity<Void> updateUser(@PathVariable(value = "id", required = true) @Valid @Positive final long id,
      @RequestBody(required = true) @Valid final UpdatableUserDto user) {
    updateUserUseCase.update(id, user);
    return ResponseEntity.noContent().build();
  }

}
