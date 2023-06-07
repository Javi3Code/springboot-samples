package org.jeycode.samples.application.users.components;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.domain.users.ports.UserDataPort;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UsernameGenerator {

  private final UserDataPort userDataPort;

  public String generate(final String name, final String lastname) {
    final var parsedName = deleteWhitespace(name);
    final var parsedLastname = deleteWhitespace(lastname);
    final var nameLength = parsedName.length();
    final var lastNameLength = parsedLastname.length();
    final var random = new Random();
    String username;
    do {
      final var firstPart = parsedName.substring(0, random.nextInt(nameLength) - 1);
      final var secondPart = parsedLastname.substring(0, random.nextInt(lastNameLength) - 1);
      username = firstPart + secondPart;
    } while (userDataPort.existsBy(username));
    return username;
  }


}
