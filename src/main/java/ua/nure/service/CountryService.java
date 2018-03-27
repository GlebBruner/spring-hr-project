package ua.nure.service;

import ua.nure.domain.Country;
import ua.nure.events.ServiceEvent;
import ua.nure.events.ServicePublisher;
import ua.nure.repository.CountryRepository;

import java.util.List;

public class CountryService {


    private CountryRepository repository;
    private ServicePublisher<CountryService> publisher;

    public void setRepository(CountryRepository repository) {
        this.repository = repository;
    }
    public void setPublisher(ServicePublisher<CountryService> publisher) {
        this.publisher = publisher;
    }

    public List<Country> findAll() {
        List<Country> foundCountries = this.repository.findAll();
        this.publisher.publish(this, ServiceEvent.OperationType.FINDALL, foundCountries);
        return repository.findAll();
    }

    public Country findOne(Integer id) {
        Country foundCountry = this.repository.findOne(id);
        this.publisher.publish(this, ServiceEvent.OperationType.FIND, foundCountry);
        return foundCountry;
    }

    public Long create(Country country) {
        publisher.publish(this, ServiceEvent.OperationType.CREATE, country);
        return this.repository.save(country);
    }

    public void delete(Integer id) {
        this.repository.delete(id);
    }

    public void update (Country country) {
        this.repository.update(country);
    }

}
