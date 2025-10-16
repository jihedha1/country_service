package net.mohamed.firstmicroservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mohamed.firstmicroservice.entities.Country;
import net.mohamed.firstmicroservice.exceptions.CountryNotFoundException;
import net.mohamed.firstmicroservice.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Transactional
@AllArgsConstructor
@Slf4j
public class CountryService {

    private CountryRepository countryRep;

    public List <Country> getAllCountries() {
        List<Country> countries = countryRep.findAll();
        return countries;
    }

    public Country getCountryById(int id) {
        List <Country> countries = countryRep.findAll();
        Country country = null;

        for (Country con:countries) {
            if (con.getId()==id)
                country = con;
        }

        return country;
    }

    public Country getCountryByName (String name) throws CountryNotFoundException {
        List <Country> countries = countryRep.findAll();
        Country country = null;

        for (Country con:countries) {
            if (con.getName().equalsIgnoreCase(name))
                country = con;
        }
        if (country==null){
            throw new CountryNotFoundException("Country Not Found");
        }

        return country;
    }

    public Country addCountry(Country country) {
        return countryRep.save(country);
    }

    public Country updateCountry(Country country) {
        return countryRep.save(country);
    }

    public void deleteCountry (Country country) {
        countryRep.delete(country);
    }
}
