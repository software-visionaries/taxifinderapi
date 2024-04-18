package app.taxifinderapi.controller;

import app.taxifinderapi.dto.LocationDTO;
import app.taxifinderapi.model.Location;
import app.taxifinderapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping("/add/location/{trip_id}")
    public LocationDTO addLocation(@RequestBody Location location, @PathVariable Long trip_id){
        return locationService.addLocation(location, trip_id);
    }
    @GetMapping("/locations")
    public List<Location> locations () {
       return locationService.locations();
    }
}
