package ua.nure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.domain.Country;
import ua.nure.repository.CountryRepository;

import java.util.List;

@Service
public class CountryService {


    private CountryRepository repository;

    @Autowired
    public void setRepository(CountryRepository repository) {
        this.repository = repository;
    }

    public List<Country> findAll() {
        return repository.findAll();
    }

    public Country findOne(Integer id) {
        return this.repository.findOne(id);
    }

    public Long create(Country country) {
        return this.repository.save(country);
    }

    public void delete(Integer id) {
        this.repository.delete(id);
    }

    public void update (Country country) {
        this.repository.update(country);
    }

}
