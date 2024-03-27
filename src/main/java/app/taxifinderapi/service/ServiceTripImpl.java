package app.taxifinderapi.service;

import app.taxifinderapi.model.TripRequest;
import app.taxifinderapi.repository.TripRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTripImpl implements ServiceTrip {

    @Autowired
    private TripRequestRepository tripRequestRepository;

    @Override
    public TripRequest saveTripRequest(TripRequest tripRequest) {
        return tripRequestRepository.save(tripRequest);
    }

    @Override
    public List<TripRequest> getAllTripRequests() {
        return tripRequestRepository.findAll();
    }

    @Override
    public List<TripRequest> getFilteredTripRequests(String fromTown, String toTown, String fromArea, String toArea, String fromSection, String toSection) {
        return tripRequestRepository.findFilteredTrips(fromTown, toTown, fromArea, toArea, fromSection, toSection);
    }
}
