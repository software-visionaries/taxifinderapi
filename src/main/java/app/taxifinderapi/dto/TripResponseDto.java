package app.taxifinderapi.dto;

import app.taxifinderapi.model.*;

import java.util.List;

public class TripResponseDto {
    private String fromTownName;
    private String  fromAreaName;
    private String fromSectionName;
    private String toTownName;
    private String  toAreaName;
    private String toSectionName;

    private List<String> taxiLocation;

    public List<String> getTaxiLocation() {
        return taxiLocation;
    }

    public void setTaxiLocation(List<String> taxiLocation) {
        this.taxiLocation = taxiLocation;
    }

    private String FarePrice;
    private String upVote;
    private String downVote;
    private String attachment;

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





    public String getFarePrice() {
        return FarePrice;
    }

    public void setFarePrice(String farePrice) {
        FarePrice = farePrice;
    }

    public String getUpVote() {
        return upVote;
    }

    public void setUpVote(String upVote) {
        this.upVote = upVote;
    }

    public String getDownVote() {
        return downVote;
    }

    public void setDownVote(String downVote) {
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
