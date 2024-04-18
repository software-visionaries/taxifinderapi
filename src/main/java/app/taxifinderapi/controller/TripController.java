package app.taxifinderapi.controller;

import app.taxifinderapi.dto.*;
import app.taxifinderapi.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@RestController
public class TripController {

    @Autowired
    private TripService tripService;
    

    @PostMapping("/add/trip/{user_id}/{question_id}")
    public TripDTO addTrip(
            @PathVariable Long user_id,
            @PathVariable Long question_id,
            @RequestParam("note") String note,
            @RequestParam("price") String price,
            @RequestParam("name") String name,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude) throws IOException {
        return tripService.addTrip(note, price, name, latitude, longitude, user_id, question_id);
    }

    @PutMapping("/add/trip/image/{trip_id}")
    public ResponseEntity<String> addImage(@PathVariable("trip_id") Long trip_id, @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        String path = "src/main/resources/static/images/signs";
        System.out.println(trip_id);
        return tripService.addImage(multipartFile, path, trip_id);
    }

    @GetMapping("direction")
    public List<TripResponseDto> getTaxiLocation(String fromTown, String fromArea, String fromSection,
            String fromNumber,
            String toTown, String toArea, String toSection, String toNumber) {
        List<TripResponseDto> tripResponseDtos = new ArrayList<>();

        return tripResponseDtos;
    }

    @GetMapping("trip/direction")
    public List<TripResponseDto> getTaxiLocation(@RequestParam(name = "fromTown") String fromTown,
            @RequestParam(name = "fromArea") String fromArea,
            @RequestParam(name = "fromSection") String fromSection, @RequestParam(name = "toTown") String toTown,
            @RequestParam(name = "toArea") String toArea, @RequestParam(name = "toSection") String toSection) {
        List<TripResponseDto> tripResponseDtos = tripService.responseTrip(fromTown, fromArea, fromSection, toTown,
                toArea, toSection);

        return tripResponseDtos;
    }
    @GetMapping("trip/{tripId}")
    public TripCoordinate singleCoordinates(@PathVariable Long tripId) {
      return tripService.tripLocation(tripId);
    }

    @GetMapping("/trips/admin")
    public List<AdminTripDto> tripDto () {
       return tripService.adminTripDtoList();
    }

    @PutMapping("/trips/admin/update/{tripId}/{locationId}")
    public TripLocationDto updateTripAdmin(@PathVariable Long tripId,@PathVariable Long locationId,
                                           @RequestBody TripLocationDto tripLocation) {
        return tripService.updateTripAdmin(tripId,locationId,tripLocation);
    }
    @DeleteMapping("/trips/admin/{tripId}/{locationId}")
    public void deleteTripLocation(Long tripId, Long locationId) {
        tripService.deleteTripLocation(tripId,locationId);
    }

}
