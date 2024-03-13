package app.taxifinderapi.service;

import app.taxifinderapi.dto.LocationDTO;
import app.taxifinderapi.model.Location;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.repository.LocationRepository;
import app.taxifinderapi.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private LocationRepository locationRepository;

    public LocationDTO addLocation(Location location, Long trip_id){

        Trip trip = tripRepository.findById(trip_id).orElse(null);
        if(trip != null){
            location.setTrip(trip);
            locationRepository.save(location);
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setLocation_id(location.getLocation_id());
            locationDTO.setName(location.getName());
            locationDTO.setLatitude(location.getLatitude());
            locationDTO.setLongitude(location.getLongitude());
            locationDTO.setTrip_id(trip.getTrip_id());
            return locationDTO;
        } else {
            return null;
        }
    }
}
