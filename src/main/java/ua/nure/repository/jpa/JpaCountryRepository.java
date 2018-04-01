package ua.nure.repository.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Country;
import ua.nure.repository.CountryRepository;
import ua.nure.repository.CrudRepository;

import java.util.List;

@Repository
@Profile("jpa")
@Primary
public class JpaCountryRepository implements CountryRepository {

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
        /*Country countryForDelete = sessionFactory.getCurrentSession().get(Country.class, id);
        if (countryForDelete != null) {
            sessionFactory.getCurrentSession().delete(countryForDelete);
        } else {
            // how to process?
        }*/
        Session session = sessionFactory.getCurrentSession();
        String deleteCountryHql = "DELETE FROM country WHERE id = :id";
        boolean isCountryDeleted = session.createQuery(deleteCountryHql).setParameter("id", id).executeUpdate() > 0;


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
