package app.taxifinderapi.dto;

public class TripCoordinate {

    private String lat;
    private String lng;

    public TripCoordinate() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
