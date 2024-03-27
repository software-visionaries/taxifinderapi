//package app.taxifinderapi.dto;
//
//import app.taxifinderapi.model.TripRequest;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TripRequestDto {
//
//    private String fromTown;
//
//    private String toTown;
//
//    public String getFromArea() {
//        return fromArea;
//    }
//
//    public void setFromArea(String fromArea) {
//        this.fromArea = fromArea;
//    }
//
//    public String getToArea() {
//        return toArea;
//    }
//
//    public void setToArea(String toArea) {
//        this.toArea = toArea;
//    }
//
//    public String getFromSection() {
//        return fromSection;
//    }
//
//    public void setFromSection(String fromSection) {
//        this.fromSection = fromSection;
//    }
//
//    public String getToSection() {
//        return toSection;
//    }
//
//    public void setToSection(String toSection) {
//        this.toSection = toSection;
//    }
//
//    private String fromArea;
//    private String toArea;
//    private String fromSection;
//    private String toSection;
//
//
//    public TripRequestDto(String fromTown, String toTown, String fromArea,
//                          String toArea, String fromSection, String toSection) {
//        this.fromTown = fromTown;
//        this.toTown = toTown;
//        this.fromArea = fromArea;
//        this.toArea = toArea;
//        this.fromSection = fromSection;
//        this.toSection = toSection;
//    }
//
//
//    public String getFromTown() {
//        return fromTown;
//    }
//
//    public void setFromTown(String fromTown) {
//        this.fromTown = fromTown;
//    }
//
//    public String getToTown() {
//        return toTown;
//    }
//
//    public void setToTown(String toTown) {
//        this.toTown = toTown;
//    }
//
//
//    public static TripRequestDto fromTripRequest(TripRequest tripRequest) {
//        return new TripRequestDto(
//                tripRequest.getFromTown(),
//                tripRequest.getToTown(),
//                tripRequest.getFromArea(),
//                tripRequest.getToArea(),
//                tripRequest.getFromSection(),
//                tripRequest.getToSection()
//        );
//    }
//
//
//    public static List<TripRequestDto> fromTripRequests(List<TripRequest> tripRequests) {
//        List<TripRequestDto> tripRequestDto = new ArrayList<>();
//        for (TripRequest tripRequest : tripRequests) {
//            tripRequestDto.add(fromTripRequest(tripRequest));
//        }
//        return tripRequestDto;
//    }
//}
package app.taxifinderapi.dto;

import app.taxifinderapi.model.TripRequest;
import java.util.ArrayList;
import java.util.List;

public class TripRequestDto {

    private String fromTown;
    private String toTown;
    private String fromArea;
    private String toArea;
    private String fromSection;
    private String toSection;

    public TripRequestDto(String fromTown, String toTown, String fromArea,
                          String toArea, String fromSection, String toSection) {
        this.fromTown = fromTown;
        this.toTown = toTown;
        this.fromArea = fromArea;
        this.toArea = toArea;
        this.fromSection = fromSection;
        this.toSection = toSection;
    }

    public String getFromTown() {
        return fromTown;
    }

    public void setFromTown(String fromTown) {
        this.fromTown = fromTown;
    }

    public String getToTown() {
        return toTown;
    }

    public void setToTown(String toTown) {
        this.toTown = toTown;
    }

    public String getFromArea() {
        return fromArea;
    }

    public void setFromArea(String fromArea) {
        this.fromArea = fromArea;
    }

    public String getToArea() {
        return toArea;
    }

    public void setToArea(String toArea) {
        this.toArea = toArea;
    }

    public String getFromSection() {
        return fromSection;
    }

    public void setFromSection(String fromSection) {
        this.fromSection = fromSection;
    }

    public String getToSection() {
        return toSection;
    }

    public void setToSection(String toSection) {
        this.toSection = toSection;
    }

    public static TripRequestDto fromTripRequest(TripRequest tripRequest) {
        return new TripRequestDto(
                tripRequest.getFromTown(),
                tripRequest.getToTown(),
                tripRequest.getFromArea(),
                tripRequest.getToArea(),
                tripRequest.getFromSection(),
                tripRequest.getToSection()
        );
    }

    public static List<TripRequestDto> fromTripRequests(List<TripRequest> tripRequests) {
        List<TripRequestDto> tripRequestDto = new ArrayList<>();
        for (TripRequest tripRequest : tripRequests) {
            tripRequestDto.add(fromTripRequest(tripRequest));
        }
        return tripRequestDto;
    }
}
