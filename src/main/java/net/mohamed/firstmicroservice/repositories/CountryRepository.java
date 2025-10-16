package net.mohamed.firstmicroservice.repositories;

import net.mohamed.firstmicroservice.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country findCountryByName(String name);
}
