package ua.nure.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.domain.Country;
import ua.nure.service.CountryService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryResource {

    private CountryService countryService;

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(this.countryService.findAll());
    }

    @GetMapping("/countries/{country_id}")
    public ResponseEntity<Country> getCountry(@PathVariable Integer country_id) {
        return ResponseEntity.ok(this.countryService.findOne(country_id));
    }

    @PostMapping("/countries")
    public ResponseEntity<Void> createCountry(@RequestBody Country country) {

        Long id = this.countryService.create(country);

        return ResponseEntity.created(URI.create("/api/countries/" + id)).build();

    }

    @DeleteMapping("/countries/{country_id}")
    public ResponseEntity<Void> deleteCountry (@PathVariable Integer country_id) {

        this.countryService.delete(country_id);
        return ResponseEntity.<Void>accepted().build();

    }

    @PutMapping("/countries")
    public ResponseEntity<Void> updateCountry(@RequestBody Country country) {

        Country country1 = countryService.findOne(country.getId().intValue());

        if (country1 == null) {
            return ResponseEntity.notFound().build();
        } else {
            this.countryService.update(country1);
            return ResponseEntity.noContent().build();
        }
    }

}