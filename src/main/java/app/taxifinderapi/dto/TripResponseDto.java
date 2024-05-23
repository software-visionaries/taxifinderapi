package app.taxifinderapi.dto;

public class TripResponseDto {
    private String fromTownName;
    private String  fromAreaName;
    private String fromSectionName;
    private String toTownName;
    private String  toAreaName;
    private String toSectionName;
    private String taxiRankLocation;
    private String taxiRankLatitude;
    private String taxiRankLongitude;
    private Long LocationId;
    private Long QuestionId;

    public Long getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(Long questionId) {
        QuestionId = questionId;
    }

    public Long getLocationId() {
        return LocationId;
    }

    public void setLocationId(Long locationId) {
        LocationId = locationId;
    }

    public String getTaxiRankLocation() {
        return taxiRankLocation;
    }

    public void setTaxiRankLocation(String taxiRankLocation) {
        this.taxiRankLocation = taxiRankLocation;
    }

    public String getTaxiRankLatitude() {
        return taxiRankLatitude;
    }

    public void setTaxiRankLatitude(String taxiRankLatitude) {
        this.taxiRankLatitude = taxiRankLatitude;
    }

    public String getTaxiRankLongitude() {
        return taxiRankLongitude;
    }

    public void setTaxiRankLongitude(String taxiRankLongitude) {
        this.taxiRankLongitude = taxiRankLongitude;
    }
//    private Location taxiLocation;
//    private List<Trip> trips;
//
//    public List<Trip> getTrips() {
//        return trips;
//    }
//
//    public void setTrips(List<Trip> trips) {
//        this.trips = trips;
//    }

//    public Location getTaxiLocation() {
//        return taxiLocation;
//    }
//
//    public void setTaxiLocation(Location taxiLocation) {
//        this.taxiLocation = taxiLocation;
//    }

    private String FarePrice;
    private Integer upVote;
    private Integer downVote;
    private String attachment;
    private Long tripId;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getToTownName() {
        return toTownName;
    }

    public void setToTownName(String toTownName) {
        this.toTownName = toTownName;
    }

    public String getToAreaName() {
        return toAreaName;
    }

    public void setToAreaName(String toAreaName) {
        this.toAreaName = toAreaName;
    }

    public String getToSectionName() {
        return toSectionName;
    }

    public void setToSectionName(String toSectionName) {
        this.toSectionName = toSectionName;
    }


    public void setFarePrice(String farePrice) {
        FarePrice = farePrice;
    }

    public String getFarePrice() {
        return FarePrice;
    }

    public Integer getUpVote() {
        return upVote;
    }

    public void setUpVote(Integer upVote) {
        this.upVote = upVote;
    }

    public Integer getDownVote() {
        return downVote;
    }

    public void setDownVote(Integer downVote) {
        this.downVote = downVote;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public TripResponseDto() {
    }
    public String getFromTownName() {
        return fromTownName;
    }

    public void setFromTownName(String fromTownName) {
        this.fromTownName = fromTownName;
    }

    public String getFromAreaName() {
        return fromAreaName;
    }

    public void setFromAreaName(String fromAreaName) {
        this.fromAreaName = fromAreaName;
    }

    public String getFromSectionName() {
        return fromSectionName;
    }

    public void setFromSectionName(String fromSectionName) {
        this.fromSectionName = fromSectionName;
    }

}
