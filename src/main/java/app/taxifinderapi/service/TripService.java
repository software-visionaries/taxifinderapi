package app.taxifinderapi.service;

import app.taxifinderapi.dto.TripDTO;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.TripRepository;
import app.taxifinderapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class TripService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    public TripDTO addTrip(MultipartFile multipartFile, String note, String price, Long user_id) throws IOException {

        String uploadDirectory = System.getProperty("user.dir") + File.separator + "src/main/resources/static/images/signs/";
        Path imagePath = Paths.get(uploadDirectory, multipartFile.getOriginalFilename());
        Files.write(imagePath, multipartFile.getBytes());

        Trip currTrip = new Trip();
        currTrip.setAttachment(multipartFile.getOriginalFilename());
        currTrip.setNote(note);
        currTrip.setPrice(price);

        User user = userRepository.findById(user_id).orElse(null);

        if(user != null){
            currTrip.setUser(user);
            Trip addedTrip = tripRepository.save(currTrip);
            TripDTO tripDTO = new TripDTO();
            tripDTO.setTripId(addedTrip.getTrip_id());
            tripDTO.setAttachment(addedTrip.getAttachment());
            tripDTO.setPrice(addedTrip.getPrice());
            tripDTO.setNote(addedTrip.getNote());
            tripDTO.setUpVote(addedTrip.getUp_vote());
            tripDTO.setDownVote(addedTrip.getDown_vote());
            return tripDTO;

        } else {
            return null;
        }
    }
}
