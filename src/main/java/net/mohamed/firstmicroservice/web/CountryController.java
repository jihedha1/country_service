package net.mohamed.firstmicroservice.web;

import lombok.AllArgsConstructor;
import net.mohamed.firstmicroservice.entities.Country;
import net.mohamed.firstmicroservice.repositories.CountryRepository;
import net.mohamed.firstmicroservice.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class CountryController {

    private CountryService countryservice;
    @GetMapping("/getcountries")
    public ResponseEntity<List <Country>> getCountries() {
        try {
            List <Country> countries = countryservice.getAllCountries();
            return new ResponseEntity <List <Country>>(countries, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountries/{id}")
    public ResponseEntity <Country> getCountryById(@PathVariable(value ="id")int id) {

        try {
            Country country = countryservice.getCountryById(id);
            return new ResponseEntity <Country>(country, HttpStatus.OK);
        }

        catch (Exception e) {
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountrie")
    public ResponseEntity <Country> getCountryByName(@RequestParam(name ="countryname")String name) {

        try {
            Country country = countryservice.getCountryByName(name);
            return new ResponseEntity <Country>(country, HttpStatus.OK);
        }

        catch (Exception e) {
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addcountry")
    public ResponseEntity <Country> addCountry(@RequestBody Country country) {

        try {
            country = countryservice.addCountry(country);
            return new ResponseEntity <Country>(country, HttpStatus.CREATED);
        }

        catch (NoSuchElementException e) {
            return new ResponseEntity <>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updatecountry/{id}")
    public ResponseEntity <Country> updateCountry(@PathVariable(value ="id")int id, @RequestBody Country country) {

        try {
            Country existCountry = countryservice.getCountryById(id);
            existCountry.setName(country.getName());
            existCountry.setCapital(country.getCapital());

            Country updatedCountry = countryservice.updateCountry(existCountry);
            return new ResponseEntity <Country>(updatedCountry, HttpStatus.OK);
        }

        catch (NoSuchElementException e) {
            return new ResponseEntity <>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deletecountry/{id}")
    public ResponseEntity <Country> deleteCountry(@PathVariable("id")int id) {
        Country country=null;
        try {
            country = countryservice.getCountryById(id);
            countryservice.deleteCountry(country);

        }

        catch (NoSuchElementException e) {
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Country>(country, HttpStatus.OK);
    }


}
