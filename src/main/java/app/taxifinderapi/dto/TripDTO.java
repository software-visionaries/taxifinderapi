package app.taxifinderapi.dto;

import java.util.List;

public class TripDTO {
    private Long tripId;
    private String attachment;
    private String price;
    private String note;
    private Integer upVote;
    private Integer downVote;

    private Long location;

    public TripDTO() {
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long locationIds) {
        this.location = location;
    }
}
