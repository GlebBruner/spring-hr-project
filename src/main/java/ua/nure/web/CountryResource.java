package ua.nure.web;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.nure.domain.Country;
import ua.nure.service.CountryService;

import java.net.URI;
import java.util.List;

//@RestController
@RequestMapping("/api")
public class CountryResource {

    private CountryService countryService;

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
        if (this.countryService.isCountryExists(country)) return new ResponseEntity<>(HttpStatus.CONFLICT);

        Long id = this.countryService.create(country);

        return ResponseEntity.created(URI.create("/api/countries/" + id)).build();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(builder.path("/countries/{country_id}").buildAndExpand(country.getId()).toUri());
//        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/countries/{country_id}")
    public ResponseEntity<Country> deleteCountry (@PathVariable Integer country_id) {

        if (this.countryService.isCountryExists(this.countryService.findOne(country_id))) {
            return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
        } else {
            this.countryService.delete(country_id);
            return new ResponseEntity<Country>(HttpStatus.NO_CONTENT);
        }

    }
}
