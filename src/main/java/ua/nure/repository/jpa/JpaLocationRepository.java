package ua.nure.repository.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Country;
import ua.nure.domain.Location;
import ua.nure.repository.CrudRepository;
import ua.nure.repository.LocationRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Profile("jpa")
@Primary
public class JpaLocationRepository implements LocationRepository {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Long save(Location location) {
        return (Long) sessionFactory.getCurrentSession().save(location);
    }

    @Override
    public Location findOne(Integer id) {
        String selectByIdNativeQuery = "SELECT * FROM location where id = :id";
        Location location = (Location) sessionFactory.getCurrentSession().createNativeQuery(selectByIdNativeQuery)
                .addEntity(Location.class)
                .setParameter("id", id).getSingleResult(); //todo native


        return sessionFactory.getCurrentSession().get(Location.class, id);
    }

    @Override
    public List<Location> findAll() {
        String selectAllNativeQuery = "SELECT * FROM location";
        List<Location> locations = sessionFactory.getCurrentSession().createNativeQuery(selectAllNativeQuery, Location.class).list(); //todo Native

        return sessionFactory.getCurrentSession().createQuery("from location").list();
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

    // cause we require joins

    public List<Location> findLocationsByCountryName(Country country) {
        String hqlJoinQuery = "select l from location l left join l.country c where c.country_name = :country_name";

        return sessionFactory.getCurrentSession().createQuery(hqlJoinQuery).setParameter("country_name", country.getCountryName()).getResultList();
    } //todo hql join

    public List<Location> findLocationsByStreetAddress(String street) {
        //also we can make it using criteria
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Location> criteriaQuery = builder.createQuery(Location.class);
        Root<Location> locationRoot = criteriaQuery.from(Location.class);
        criteriaQuery.select(locationRoot).where(builder.equal(locationRoot.get("street_address"), street));
        return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getResultList();
    }



}
