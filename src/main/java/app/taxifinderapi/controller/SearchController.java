package app.taxifinderapi.controller;

import app.taxifinderapi.dto.TripRequestDto;
import app.taxifinderapi.dto.TripResponseDto;
import app.taxifinderapi.model.TripRequest;
import app.taxifinderapi.service.ServiceTrip;
import app.taxifinderapi.service.ServiceTripImpl;
import app.taxifinderapi.service.TripService;
import app.taxifinderapi.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    @Autowired
    private ServiceTrip serviceTrip;

    @GetMapping("/search")
    public ResponseEntity<List<TripRequestDto>> filterTrips(
            @RequestParam(required = false) String fromTown,
            @RequestParam(required = false) String toTown,
            @RequestParam(required = false) String fromArea,
            @RequestParam(required = false) String toArea,
            @RequestParam(required = false) String fromSection,
            @RequestParam(required = false) String toSection) {

        List<TripRequest> tripRequests = serviceTrip.getFilteredTripRequests(fromTown, toTown, fromArea, toArea, fromSection, toSection);

        List<TripRequestDto> tripRequestDtoList = tripRequests.stream()
                .map(TripRequestDto::fromTripRequest)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tripRequestDtoList);
    }

    @PostMapping("/trips")
    public ResponseEntity<String> saveTripRequest(@RequestBody TripRequestDto tripRequestDto) {
        TripRequest tripRequest = EntityDtoMapper.mapToEntity(tripRequestDto);
        serviceTrip.saveTripRequest(tripRequest);
        return new ResponseEntity<>("Trip request saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TripRequest>> getAllTrips() {
        List<TripRequest> trips = serviceTrip.getAllTripRequests();
        return ResponseEntity.ok(trips);
    }


}
