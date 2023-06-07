package org.jeycode.samples.domain.users.models;

import java.io.Serializable;

public record User(
    long id,
    String username,
    String firstName,
    String lastName,
    String email,
    String address,
    String phoneNumber
) implements Serializable {

}
