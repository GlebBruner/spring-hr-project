package ua.nure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.domain.Country;
import ua.nure.events.ServiceEvent;
import ua.nure.events.ServicePublisher;
import ua.nure.repository.CountryRepository;
import ua.nure.repository.jdbc.CountryRepositoryImpl;

import java.util.List;

@Service
public class CountryService {

    private CountryRepository repository;
    private ServicePublisher<CountryService> publisher;

    @Autowired
    public void setRepository(CountryRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPublisher(ServicePublisher<CountryService> publisher) {
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public List<Country> findAll() {
        List<Country> foundCountries = this.repository.findAll();
        this.publisher.publish(this, ServiceEvent.OperationType.FINDALL, foundCountries);
        return repository.findAll();
    }

    @Transactional(readOnly = true)
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
