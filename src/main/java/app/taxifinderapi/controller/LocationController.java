package app.taxifinderapi.controller;

import app.taxifinderapi.dto.LocationDTO;
import app.taxifinderapi.dto.TripResponseDto;
import app.taxifinderapi.model.Location;
import app.taxifinderapi.model.TripRequest;
import app.taxifinderapi.service.LocationService;
import app.taxifinderapi.service.TripService;
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

    @Autowired
    private TripService tripService;

    @PostMapping("/response")
    public List<TripResponseDto> responseTrip(@RequestBody TripRequest tripRequest) {
        return tripService.responseTrip(tripRequest.getFromTown(), tripRequest.getFromArea(), tripRequest.getFromSection(),
                tripRequest.getToTown(), tripRequest.getToArea(), tripRequest.getToSection());
    }

}
