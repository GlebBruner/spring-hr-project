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
}
