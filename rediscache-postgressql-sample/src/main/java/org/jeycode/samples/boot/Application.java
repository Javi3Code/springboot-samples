package org.jeycode.samples.boot;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void initMockData() {
    final var fakeDataGenerator = new Faker();
//    IntStream.range(0,1000).
  }

}
