package app.taxifinderapi.dto;

import app.taxifinderapi.model.Location;
import app.taxifinderapi.model.Trip;

public class TripLocationDto {
    private Location location;
    private Trip trip;

    public TripLocationDto() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
