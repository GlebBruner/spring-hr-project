package ua.nure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.domain.Location;
import ua.nure.repository.LocationRepository;

import java.util.List;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAll () {
        return locationRepository.findAll();
    }

    public Location findOne (Integer id) {
        return locationRepository.findOne(id);
    }

    public Long create(Location location) {
        return this.locationRepository.save(location);
    }

    public void delete(Integer id) {
        this.locationRepository.delete(id);
    }

    public void update(Location location) {
        this.locationRepository.update(location);
    }

}
