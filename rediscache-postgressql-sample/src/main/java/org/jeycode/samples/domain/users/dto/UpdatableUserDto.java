package org.jeycode.samples.domain.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UpdatableUserDto(@Positive long id,
                               @NotBlank String firstName,
                               @NotBlank String lastName,
                               @Email String email,
                               @NotBlank String address,
                               @NotBlank String phoneNumber) {

}
