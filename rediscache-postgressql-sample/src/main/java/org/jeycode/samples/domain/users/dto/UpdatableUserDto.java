package org.jeycode.samples.domain.users.dto;

import jakarta.validation.constraints.Email;

public record UpdatableUserDto(
    String firstName,
    String lastName,
    @Email String email,
    String address,
    String phoneNumber) {

}