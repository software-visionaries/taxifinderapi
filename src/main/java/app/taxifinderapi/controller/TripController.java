package app.taxifinderapi.controller;

import app.taxifinderapi.dto.TripDTO;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/add/trip/{user_id}")
    public TripDTO addTrip(@PathVariable Long user_id,
                           @RequestPart("attachment") MultipartFile multipartFile,
                           @RequestParam("note") String note,
                           @RequestParam("price") String price ) throws IOException {
        return tripService.addTrip(multipartFile, note, price, user_id);
    }
}
