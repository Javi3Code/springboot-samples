package org.jeycode.samples.domain.users.models;
public record User(
    long id,
    String username,
    String firstName,
    String lastName,
    String email,
    String address,
    String phoneNumber
) {

}
