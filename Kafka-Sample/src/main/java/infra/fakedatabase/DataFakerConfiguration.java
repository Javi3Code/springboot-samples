package infra.fakedatabase;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataFakerConfiguration {

  @Bean
  public Faker faker() {
    return new Faker();
  }

}
