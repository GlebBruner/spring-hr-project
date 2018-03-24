package ua.nure.web;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.nure.domain.Location;
import ua.nure.service.LocationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationResource {

    private LocationService locationService;

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations () {
        return ResponseEntity.ok(this.locationService.findAll());
    }

    @GetMapping("/locations/{location_id}")
    public ResponseEntity<Location> getLocation(@PathVariable Integer location_id) {
        return ResponseEntity.ok(this.locationService.findOne(location_id));
    }

    @PostMapping("/locations")
    public ResponseEntity<Void> createLocation(@PathVariable Location location, UriComponentsBuilder componentsBuilder) {

        if (this.locationService.isLocationExists(location)) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        } else {
            Long id = this.locationService.create(location);
            return ResponseEntity.created(URI.create("/api/locations/" + id)).build();
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(componentsBuilder.path("/location/{location_id}").buildAndExpand(location.getId()).toUri());
//            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/locations/{location_id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable Integer location_id) {
        if (this.locationService.isLocationExists(this.locationService.findOne(location_id))) {
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        } else {
            this.locationService.delete(location_id);
            return new ResponseEntity<Location>(HttpStatus.NO_CONTENT);
        }
    }

}
