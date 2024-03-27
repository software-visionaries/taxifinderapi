package app.taxifinderapi.service;

import app.taxifinderapi.model.TripRequest;

import java.util.List;

public interface ServiceTrip {
    TripRequest saveTripRequest(TripRequest tripRequest);
    List<TripRequest> getAllTripRequests();
    List<TripRequest> getFilteredTripRequests(String fromTown, String toTown, String fromArea, String toArea, String fromSection, String toSection);
}
