package app.taxifinderapi.dto;

public class AdminTripDto {
    private String fromQuestion;
    private String toQuestion;
    private String taxiLocation;
    private String taxiPrice;
    private Long locationId;
    private Long tripId;

    public AdminTripDto() {
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getFromQuestion() {
        return fromQuestion;
    }

    public void setFromQuestion(String fromQuestion) {
        this.fromQuestion = fromQuestion;
    }

    public String getToQuestion() {
        return toQuestion;
    }

    public void setToQuestion(String toQuestion) {
        this.toQuestion = toQuestion;
    }

    public String getTaxiLocation() {
        return taxiLocation;
    }

    public void setTaxiLocation(String taxiLocation) {
        this.taxiLocation = taxiLocation;
    }

    public String getTaxiPrice() {
        return taxiPrice;
    }

    public void setTaxiPrice(String taxiPrice) {
        this.taxiPrice = taxiPrice;
    }
}
