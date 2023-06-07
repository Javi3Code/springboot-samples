package org.jeycode.samples.domain.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegistrableDto(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @Email String email,
    @NotBlank String address,
    @NotBlank String phoneNumber) {

}
