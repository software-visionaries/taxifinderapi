package app.taxifinderapi.controller;

import app.taxifinderapi.dto.TripDTO;
import app.taxifinderapi.dto.TripResponseDto;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.service.FileSystemStorageService;
import app.taxifinderapi.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @PostMapping("/add/trip/{user_id}")
    public TripDTO addTrip(@PathVariable Long user_id,
                           @RequestPart("attachment") MultipartFile multipartFile,
                           @RequestParam("note") String note,
                           @RequestParam("price") String price ) throws IOException {
        return tripService.addTrip(multipartFile, note, price, user_id);
    }

    @GetMapping("direction")
    public List<TripResponseDto> getTaxiLocation(String fromTown, String fromArea, String fromSection, String fromNumber,
                                                 String toTown, String toArea, String toSection, String toNumber) {
        List<TripResponseDto> tripResponseDtos = new ArrayList<>();

        return tripResponseDtos;
    }


    @GetMapping("trip/direction")
    public List<TripResponseDto> getTaxiLocation(@RequestParam(name = "fromTown") String fromTown,@RequestParam(name = "fromArea") String fromArea,
                                                 @RequestParam(name = "fromSection") String fromSection, @RequestParam(name = "toTown") String toTown,
                                                 @RequestParam(name = "toArea") String toArea,@RequestParam(name = "toSection") String toSection) {
        List<TripResponseDto> tripResponseDtos = tripService.responseTrip(fromTown,fromArea,fromSection,toTown,toArea,toSection);

        return tripResponseDtos;
    }
}
