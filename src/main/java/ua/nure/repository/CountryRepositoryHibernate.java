package ua.nure.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Country;

import java.util.List;

@Repository
public class CountryRepositoryHibernate implements CrudRepository<Country> {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Long save(Country country) {
        return (Long) sessionFactory.getCurrentSession().save(country);
    }

    @Override
    public Country findOne(Integer id) {
        return sessionFactory.getCurrentSession().get(Country.class, id);
    }

    @Override
    public List<Country> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM country").list();
    }

    @Override
    public void delete(Integer id) {
        Country countryForDelete = sessionFactory.getCurrentSession().get(Country.class, id);
        if (countryForDelete != null) {
            sessionFactory.getCurrentSession().delete(countryForDelete);
        } else {
            // how to process?
        }
    }

    @Override
    public void update(Country country) {
        Country countryForUpdate = sessionFactory.getCurrentSession().get(Country.class, country.getId());
        if (countryForUpdate != null) {
            countryForUpdate.setCountryName(country.getCountryName());
            sessionFactory.getCurrentSession().update(countryForUpdate);
        }
    }
}
