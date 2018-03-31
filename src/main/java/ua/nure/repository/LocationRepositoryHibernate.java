package ua.nure.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Location;

import java.util.List;

@Repository
public class LocationRepositoryHibernate implements CrudRepository<Location> {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Long save(Location location) {
        return (Long) sessionFactory.getCurrentSession().save(location);
    }

    @Override
    public Location findOne(Integer id) {
        return sessionFactory.getCurrentSession().get(Location.class, id);
    }

    @Override
    public List<Location> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM location").list();
    }

    @Override
    public void delete(Integer id) {
        Location locationForDelete = findOne(id);
        if (locationForDelete != null) {
            sessionFactory.getCurrentSession().delete(locationForDelete);
        }
    }

    @Override
    public void update(Location location) {
        Location locationForUpdate = findOne(location.getId().intValue());
        if (locationForUpdate != null) {
            locationForUpdate.setCity(location.getCity());
            locationForUpdate.setCountry(location.getCountry());
            locationForUpdate.setStreetAddress(location.getStreetAddress());
            locationForUpdate.setPostalCode(location.getPostalCode());
            sessionFactory.getCurrentSession().update(locationForUpdate);
        }
    }
}
