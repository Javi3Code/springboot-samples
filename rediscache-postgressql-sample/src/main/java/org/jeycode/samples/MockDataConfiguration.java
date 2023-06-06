package org.jeycode.samples;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Year;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.jeycode.samples.infra.books.jpa_entities.BookEntity;
import org.jeycode.samples.infra.books.repositories.BookRepository;
import org.jeycode.samples.infra.users.jpa_entities.UserEntity;
import org.jeycode.samples.infra.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "INIT_DATA")
@Configuration
public class MockDataConfiguration {

  @Value("${mocks.books:1000}")
  private final int books;
  @Value("${mocks.users:10}")
  private final int users;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  @PostConstruct
  @Transactional
  public void initMockData() {
    final var fakeDataGenerator = new Faker();
    final var generatedBooks = IntStream.range(0, books)
        .mapToObj(i -> {
              final var book = new BookEntity();
              book.setIsbn(UUID.randomUUID().toString());
              book.setTitle(fakeDataGenerator.book().title());
              book.setAuthor(fakeDataGenerator.book().author());
              book.setGenre(BookGenre.values()[fakeDataGenerator.number().numberBetween(0, 15)]);
              book.setAvailableCopies((byte) fakeDataGenerator.number().numberBetween(0, 100));
              book.setPricePerDay(BigDecimal.valueOf(fakeDataGenerator.number().randomDouble(2, 1, 100)));
              book.setPublicationYear(Year.of(fakeDataGenerator.number().numberBetween(1600, 2023)));
              book.setDescription(fakeDataGenerator.lorem().sentence());
              return book;
            }
        ).toList();

    bookRepository.saveAll(generatedBooks);

    final var generatedUsers = IntStream.range(0, users)
        .mapToObj(i -> {
          final var user = new UserEntity();
          user.setUsername(fakeDataGenerator.name().username());
          user.setFirstName(fakeDataGenerator.name().firstName());
          user.setLastName(fakeDataGenerator.name().lastName());
          user.setEmail(fakeDataGenerator.internet().emailAddress());
          user.setAddress(fakeDataGenerator.address().fullAddress());
          user.setPhoneNumber(fakeDataGenerator.phoneNumber().cellPhone());
          return user;
        })
        .toList();

    userRepository.saveAll(generatedUsers);

    auditor.info("Generated books: " + generatedBooks.size());
    auditor.info("Generated users: " + generatedUsers.size());
  }
}
