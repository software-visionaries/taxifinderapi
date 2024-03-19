package app.taxifinderapi.service;

import app.taxifinderapi.dto.TripDTO;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.TripRepository;
import app.taxifinderapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
public class TripService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TripRepository tripRepository;

    public void saveImage(MultipartFile multipartFile, String path) throws IOException {
        String uploadDirectory = System.getProperty("user.dir") + File.separator + path;
        Path uploadPath = Paths.get(uploadDirectory);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String fileExtension = getFileExtension(multipartFile);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Path imagePath = uploadPath.resolve(fileName + fileExtension);
        Files.write(imagePath, multipartFile.getBytes());
    }

    public String getFileExtension(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if(contentType == null){
            return "";
        }

        String[] extensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};

        for(String extension: extensions) {
            if(contentType.contains(extension.substring(1))){
                return  extension;
            }
        }

        return "";
    }

    public TripDTO addTrip(MultipartFile multipartFile, String note, String price, Long user_id) throws IOException {

        String path = "src/main/resources/static/images/signs";
        saveImage(multipartFile, path);
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
    
    private List<TripResponseDto> responseTrip(String fromTown, String fromArea, String fromSection, String fromNumber,
                                               String toTown, String toArea, String toSection, String toNumber) {
        List<TripResponseDto> tripResponse = new ArrayList<>();
        return  tripResponse;
    }
}
