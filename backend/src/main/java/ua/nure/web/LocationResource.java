package ua.nure.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.domain.Location;
import ua.nure.service.LocationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationResource {

    private LocationService locationService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(this.locationService.findAll());
    }

    @GetMapping("/locations/{location_id}")
    public ResponseEntity<Location> getLocation(@PathVariable Integer location_id) {
        return ResponseEntity.ok(this.locationService.findOne(location_id));
    }

    @PostMapping("/locations")
    public ResponseEntity<Void> createLocation(@RequestBody Location location) {

        Long id = this.locationService.create(location);
        return ResponseEntity.created(URI.create("/api/locations/" + id)).build();
    }

    @DeleteMapping("/locations/{location_id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable Integer location_id) {
        this.locationService.delete(location_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/locations")
    public ResponseEntity<Void> updateLocation (@RequestBody Location location) {

        Location updatedLocation = this.locationService.findOne(location.getId().intValue());

        if (updatedLocation == null) {
            return ResponseEntity.notFound().build();
        } else {
            this.locationService.update(location);
            return ResponseEntity.noContent().build();
        }

    }
}
