package ua.nure.service;

import ua.nure.domain.Country;
import ua.nure.repository.CountryRepository;

import java.util.List;

public class CountryService {


    private CountryRepository repository;

    public void setRepository(CountryRepository repository) {
        this.repository = repository;
    }

    public List<Country> findAll() {
        return repository.findAll();
    }

    public Country findOne(Integer id) {
        return this.repository.findOne(id);
    }

    public void create(Country country) {

        this.repository.save(country);
    }

    public void delete(Integer id) {
        this.repository.delete(id);
    }

    public boolean isCountryExists (Country country) {
        return this.repository.findOne(country.getId().intValue()) != null;
    }
}
