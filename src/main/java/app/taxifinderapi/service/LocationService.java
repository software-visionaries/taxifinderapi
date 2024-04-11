package app.taxifinderapi.service;

import app.taxifinderapi.dto.LocationDTO;
import app.taxifinderapi.model.Location;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.LocationRepository;
import app.taxifinderapi.repository.TripRepository;
import app.taxifinderapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

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

    public List<Location> locations (Long userId, Long tripId) {
        Trip trip =tripRepository.findById(tripId).get();
        User user = userRepository.findById(userId).get();

        List<Location> locations = trip.getLocation();
        List<Location> useraddLocationsToTrip = new ArrayList<>();

        for (Location location : locations) {
            if(location.getTrip().equals(trip) &&
                    (user.getTrips().equals(trip.getUser()))) {
                useraddLocationsToTrip.add(location);
            }
        }
        return useraddLocationsToTrip;
    }

    public Location location (Long locationId, Long tripId,Long userId) {
       Location locationTemp = null;
      List<Location> locations = locations(userId,tripId);
      for (Location location :locations) {
          if(location.getLocation_id().equals(locationId)) {
              locationTemp = location;
          }
      }
      return locationTemp;
    }
}
