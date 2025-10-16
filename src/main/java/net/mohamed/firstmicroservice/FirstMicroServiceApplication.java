package net.mohamed.firstmicroservice;

import net.mohamed.firstmicroservice.entities.Country;
import net.mohamed.firstmicroservice.repositories.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class FirstMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstMicroServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CountryRepository countryRepository) {
        return args -> {
            Stream.of("Tunisie", "France", "germany").forEach(name -> {
                Country country = new Country();
                country.setName(name);
                country.setCapital(name);
                countryRepository.save(country);
            });
        };
    }



}
