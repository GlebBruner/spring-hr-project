package ua.nure.web;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.domain.Location;
import ua.nure.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationResource {

    private LocationService locationService;

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping("/location")
    public ResponseEntity<List<Location>> getAllLocations () {
        return ResponseEntity.ok(this.locationService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET , value = "/location/{location_id}")
    public Location getLocation(@PathVariable Integer location_id) {
        return this.locationService.findOne(location_id);
    }
}
