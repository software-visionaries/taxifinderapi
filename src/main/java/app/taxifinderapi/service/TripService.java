package app.taxifinderapi.service;

import app.taxifinderapi.dto.TripDTO;
import app.taxifinderapi.dto.TripResponseDto;
import app.taxifinderapi.model.*;
import app.taxifinderapi.repository.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        Question question = findQuestion(fromQuestion,toQuestion);
//        List<Trip> trips = findTrips(question);

        List<String> taxiLocation = new ArrayList<>();

        TripResponseDto responseDto = new TripResponseDto();
//        for(Trip trip : trips)
//             System.out.println(trips);


            responseDto.setFromAreaName(currentArea.getName());
            responseDto.setFromTownName(currentArea.getName());
            responseDto.setFromSectionName(currentSection.getName());
            responseDto.setToTownName(destinationTown.getName());
            responseDto.setToAreaName(destinationArea.getName());
            responseDto.setToSectionName(destinationSection.getName());
//            responseDto.
            tripResponses.add(responseDto);


        return tripResponses;
    }

    public Address findAddress(Town town, Area area, Section section) {
        List<Address> addresses = addressRepository.findAll();

        Optional<Address> foundAddress = addresses.stream()
                .filter(address ->
                        address.getArea() != null &&
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
                .filter(question ->
                        question.getToQuestion() != null &&
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

    public String tripFlag(boolean isNotEmpty) {
        return "No responses found we added the query to our community for the future.";
    }
}
