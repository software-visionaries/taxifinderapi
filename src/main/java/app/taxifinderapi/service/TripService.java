package app.taxifinderapi.service;

import app.taxifinderapi.dto.TripDTO;
import app.taxifinderapi.dto.TripResponseDto;
import app.taxifinderapi.model.*;
import app.taxifinderapi.repository.*;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ToQuestionRepository toQuestionRepository;

    @Autowired
    private FromQuestionRepository fromQuestionRepository;

    @Autowired
    LocationRepository locationRepository;

    public void saveImage(MultipartFile multipartFile, String path) throws IOException {
        String uploadDirectory = System.getProperty("user.dir") + File.separator + path;
        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileExtension = getFileExtension(multipartFile);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Path imagePath = uploadPath.resolve(fileName + fileExtension);
        Files.write(imagePath, multipartFile.getBytes());
    }

    public String getFileExtension(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            return "";
        }

        String[] extensions = { ".jpg", ".jpeg", ".png", ".gif", ".bmp" };

        for (String extension : extensions) {
            if (contentType.contains(extension.substring(1))) {
                return extension;
            }
        }

        return "";
    }

    public ResponseEntity<String> addImage(MultipartFile multipartFile, String path, Long trip_id) throws IOException {
        saveImage(multipartFile, path);
        Trip trip = tripRepository.findById(trip_id).orElse(null);
        trip.setAttachment(multipartFile.getOriginalFilename());
        return ResponseEntity.ok().body("Image was successfully saved");
    }

    @Transactional
    public TripDTO addTrip(
            String note,
            String price,
            String name,
            String latitude,
            String longitude,
            Long user_id,
            Long question_id) throws IOException {

        Trip currTrip = new Trip();
        currTrip.setNote(note);
        currTrip.setPrice(price);

        Location location = new Location(name, latitude, longitude);
        location.setTrip(currTrip);
        locationRepository.save(location);

        User user = userRepository.findById(user_id).orElse(null);
        Question question = questionRepository.findById(question_id).orElse(null);

        if (user != null && question != null) {
            currTrip.setUser(user);
            currTrip.setQuestion(question);
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

    public List<TripResponseDto> responseTrip(String fromTown, String fromArea, String fromSection,
            String toTown, String toArea, String toSection) {

        List<TripResponseDto> tripResponses = new ArrayList<>();

        Town currentTown = townRepository.findByName(fromTown);
        Area currentArea = areaRepository.findByName(fromArea);
        Section currentSection = sectionRepository.findByName(fromSection);

        Town destinationTown = townRepository.findByName(toTown);
        Area destinationArea = areaRepository.findByName(toArea);
        Section destinationSection = sectionRepository.findByName(toSection);

        Address fromAddress = findAddress(currentTown, currentArea, currentSection);
        Address toAddress = findAddress(destinationTown, destinationArea, destinationSection);
        ToQuestion toQuestion = toQuestionRepository.findByAddress(toAddress);
        FromQuestion fromQuestion = fromQuestionRepository.findByAddress(fromAddress);
        Question question = findQuestion(fromQuestion, toQuestion);
        List<Trip> trips = findTrips(question);

        for (Trip trip : trips) {
            TripResponseDto responseDto = new TripResponseDto();
            responseDto.setFromAreaName(currentArea.getName());
            responseDto.setFromTownName(currentTown.getName());
            responseDto.setFromSectionName(currentSection.getName());
            responseDto.setToTownName(destinationTown.getName());
            responseDto.setToAreaName(destinationArea.getName());
            responseDto.setToSectionName(destinationSection.getName());
            responseDto.setFarePrice(trip.getPrice());
            responseDto.setUpVote(trip.getUp_vote());
            responseDto.setDownVote(trip.getDown_vote());
            List<Location> taxiStand = trip.getLocation();
            taxiStand.stream()
                    .forEach(tempLocation -> {
                        responseDto.setTaxiRankLatitude(tempLocation.getLatitude());
                        responseDto.setTaxiRankLongitude(tempLocation.getLongitude());
                        responseDto.setTaxiRankLocation(tempLocation.getName());
                    });
            responseDto.setAttachment(trip.getAttachment());
            responseDto.setTripId(trip.getTrip_id());
            tripResponses.add(responseDto);
        }
        return tripResponses;
    }

    public Address findAddress(Town town, Area area, Section section) {
        List<Address> addresses = addressRepository.findAll();

        Optional<Address> foundAddress = addresses.stream()
                .filter(address -> address.getArea() != null &&
                        address.getTown() != null &&
                        address.getSection() != null &&
                        address.getArea().equals(area) &&
                        address.getTown().equals(town) &&
                        address.getSection().equals(section))
                .findFirst();

        return foundAddress.orElse(null);
    }

    public Question findQuestion(FromQuestion fromQuestion, ToQuestion toQuestion) {
        List<Question> questions = questionRepository.findAll();

        Optional<Question> foundQuestion = questions.stream()
                .filter(question -> question.getToQuestion() != null &&
                        question.getFromQuestion() != null &&
                        question.getFromQuestion().equals(fromQuestion) &&
                        question.getToQuestion().equals(toQuestion))
                .findFirst();

        return foundQuestion.orElse(null);
    }

    public List<Trip> findTrips(Question question) {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream()
                .filter(trip -> trip.getQuestion() != null && trip.getQuestion().equals(question))
                .collect(Collectors.toList());
    }

}
