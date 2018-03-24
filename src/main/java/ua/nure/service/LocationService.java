package ua.nure.service;

import ua.nure.domain.Location;
import ua.nure.repository.LocationRepository;

import java.util.List;

public class LocationService {

    private LocationRepository locationRepository;

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

    public boolean isLocationExists(Location location) {
//        return this.locationRepository.findOne(location.getId().intValue()) != null;
        return false;
    }

}
