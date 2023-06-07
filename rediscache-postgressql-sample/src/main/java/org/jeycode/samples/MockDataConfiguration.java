package org.jeycode.samples;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
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

    logger.info("Generating books: " + generatedBooks.size());
    logger.info("Generating users: " + generatedUsers.size());

    final var batchSize = 3000;
    final var maxThreads = 8;
    final var threadCount = Math.min((generatedBooks.size() + batchSize - 1) / batchSize, maxThreads);
    final var threadPool = Executors.newFixedThreadPool(threadCount);

    for (int i = 0; i < generatedUsers.size(); i += batchSize) {
      final List<UserEntity> batch = generatedUsers.subList(i, Math.min(i + batchSize, generatedUsers.size()));
      CompletableFuture.runAsync(() ->
          userRepository.saveAllAndFlush(batch), threadPool);
    }

    for (int i = 0; i < generatedBooks.size(); i += batchSize) {
      final List<BookEntity> batch = generatedBooks.subList(i, Math.min(i + batchSize, generatedBooks.size()));
      CompletableFuture.runAsync(() ->
          bookRepository.saveAllAndFlush(batch), threadPool);
    }

    threadPool.shutdown();

  }
}
