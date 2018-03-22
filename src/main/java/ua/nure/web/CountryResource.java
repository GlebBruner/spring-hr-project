package ua.nure.web;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nure.domain.Country;
import ua.nure.service.CountryService;

import java.util.List;

//@RestController
@RequestMapping("/api")
public class CountryResource {

    private CountryService countryService;

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping("/countries")
    @ResponseBody
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(this.countryService.findAll());
    }
}
