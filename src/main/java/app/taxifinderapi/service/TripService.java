package app.taxifinderapi.service;

import app.taxifinderapi.dto.*;
import app.taxifinderapi.exceptions.TripException;
import app.taxifinderapi.exceptions.UserException;
import app.taxifinderapi.model.*;
import app.taxifinderapi.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

    @Autowired
    QuestionService questionService;


    public void saveImage(MultipartFile multipartFile, String path) throws IOException {
        String uploadDirectory = System.getProperty("user.dir") + File.separator + path;
        Path uploadPath = Paths.get(uploadDirectory);


        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // String fileExtension = getFileExtension(multipartFile);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Path imagePath = uploadPath.resolve(fileName);
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
        System.out.println(multipartFile.getOriginalFilename());
        System.out.println(trip.getPrice());
        trip.setAttachment(multipartFile.getOriginalFilename());
        tripRepository.save(trip);
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

        Town currentTown = townRepository.findByName(questionService.word(fromTown));
        Area currentArea = areaRepository.findByName(questionService.word(fromArea));
        Section currentSection = sectionRepository.findByName(fromSection);

        Town destinationTown = townRepository.findByName(questionService.word(toTown));
        Area destinationArea = areaRepository.findByName(questionService.word(toArea));
        Section destinationSection = sectionRepository.findByName(toSection);

        Address fromAddress = findAddress(currentTown, currentArea, currentSection);
        Address toAddress = findAddress(destinationTown, destinationArea, destinationSection);
        ToQuestion toQuestion = toQuestionRepository.findByAddress(toAddress);
        FromQuestion fromQuestion = fromQuestionRepository.findByAddress(fromAddress);
        Question question = findQuestion(fromQuestion, toQuestion);
        List<Trip> trips = findTrips(question);

        return trips.stream().map(trip -> {
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
            taxiStand.forEach(tempLocation -> {
                responseDto.setTaxiRankLatitude(tempLocation.getLatitude());
                responseDto.setTaxiRankLongitude(tempLocation.getLongitude());
                responseDto.setTaxiRankLocation(tempLocation.getName());
                responseDto.setLocationId(tempLocation.getLocation_id());
            });
            responseDto.setAttachment(trip.getAttachment());
            responseDto.setTripId(trip.getTrip_id());
            return responseDto;
        }).collect(Collectors.toList());
    }


    public TripCoordinate tripLocation(Long tripId) {
        Location location= locationRepository.findById(tripId).get();
        TripCoordinate tripCoordinate = new TripCoordinate();
        tripCoordinate.setLat(location.getLatitude());
        tripCoordinate.setLng(location.getLongitude());
        return tripCoordinate;
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

    public List<Trip> userTrip(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserException("User doesn't exists");
        });
        List<Trip> trips = tripRepository.findByUser(user);
        return trips;
    }

    public Trip updateUserTrip(Trip tripRequest, Long tripId, Long userId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripException("Trip not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        if (!trip.getUser().getUser_id().equals(userId)) {
            throw new IllegalArgumentException("User is not authorized to update this trip");
        }

        Trip updatedTrip = new Trip();
        if (tripRequest.getAttachment() != null && !tripRequest.getAttachment().equals(trip.getAttachment())) {
            updatedTrip.setAttachment(tripRequest.getAttachment());
        }
        if (tripRequest.getPrice() != null && !tripRequest.getPrice().equals(trip.getPrice())) {
            updatedTrip.setPrice(tripRequest.getPrice());
        }

        List<Location> locations = trip.getLocation();

        List<Location> userLocations = locations.stream()
                .filter(loc -> loc.getTrip().getUser().equals(user))
                .collect(Collectors.toList());

        if (userLocations.size() == 1) {
            Location location = userLocations.get(0);
            if (tripRequest.getLocation() != null && !tripRequest.getLocation().equals(location)) {
                location = tripRequest.getLocation().get(0);
            }
            updatedTrip.setLocation(List.of(location));
        } else if (userLocations.size() > 1) {

            throw new IllegalStateException("Multiple locations found for the user. User needs to select one to update.");
        } else {
            throw new IllegalStateException("No location found for the user.");
        }

        return tripRepository.save(updatedTrip);
    }

    public List<AdminTripDto> adminTripDtoList() {
        List<Trip> trips = tripRepository.findAll();
        Map<Long, Question> questionMap = new HashMap<>();
        Map<Long, FromQuestion> fromQuestionMap = new HashMap<>();
        Map<Long, ToQuestion> toQuestionMap = new HashMap<>();
        Map<Long, Address> addressMap = new HashMap<>();
        Map<Long, Town> townMap = new HashMap<>();
        Map<Long, Area> areaMap = new HashMap<>();
        Map<Long, Section> sectionMap = new HashMap<>();
        Map<Long,Location> locationMap = new HashMap<>();


        questionRepository.findAll().forEach(question -> questionMap.put(question.getQuestionId(), question));
        fromQuestionRepository.findAll().forEach(fromQuestion -> fromQuestionMap.put(fromQuestion.getId(), fromQuestion));
        toQuestionRepository.findAll().forEach(toQuestion -> toQuestionMap.put(toQuestion.getId(), toQuestion));
        addressRepository.findAll().forEach(address -> addressMap.put(address.getId(), address));
        townRepository.findAll().forEach(town -> townMap.put(town.getId(), town));
        areaRepository.findAll().forEach(area -> areaMap.put(area.getId(), area));
        sectionRepository.findAll().forEach(section -> sectionMap.put(section.getId(), section));
        locationRepository.findAll().forEach(location -> locationMap.put(location.getLocation_id(),location));

        List<AdminTripDto> adminTripDtos = new ArrayList<>();

        for (Trip trip : trips) {
            AdminTripDto tripDto = new AdminTripDto();
            Question question = null;
            if (trip.getQuestion() != null) {
                question = questionMap.get(trip.getQuestion().getQuestionId());

            }
            if (question != null) {
                FromQuestion fromQuestion = fromQuestionMap.get(question.getFromQuestion().getId());
                ToQuestion toQuestion = toQuestionMap.get(question.getToQuestion().getId());
                Address addressTo = addressMap.get(toQuestion.getAddress().getId());
                Address addressFrom = addressMap.get(fromQuestion.getAddress().getId());
                Town townTo = townMap.get(addressTo.getTown().getId());
                Town townFrom = townMap.get(addressFrom.getTown().getId());



                Area areaTo = null;
                Area areaFrom = null;
                Section sectionTo = null;
                Section sectionFrom = null;
                Location taxilocation = null;

                for (Area area : townTo.getAreas()) {
                    if (areaMap.containsKey(area.getId())) {
                        areaTo = areaMap.get(area.getId());
                        break;
                    }
                }
                for (Area area : townFrom.getAreas()) {
                    if (areaMap.containsKey(area.getId())) {
                        areaFrom = areaMap.get(area.getId());
                        break;
                    }
                }


                if (areaTo != null && areaFrom != null) {
                    for (Section section : areaTo.getSections()) {
                        if (sectionMap.containsKey(section.getId())) {
                            sectionTo = sectionMap.get(section.getId());
                            break;
                        }
                    }
                    for (Section section : areaFrom.getSections()) {
                        if (sectionMap.containsKey(section.getId())) {
                            sectionFrom = sectionMap.get(section.getId());
                            break;
                        }
                    }
                }

                if(trip != null) {
                    for (Location location : trip.getLocation()) {
                        if(locationMap.containsKey(location.getLocation_id())) {
                            taxilocation = locationMap.get(location.getLocation_id());
                        }
                    }
                }
                tripDto.setFromQuestion(townFrom.getName() + ", " + areaFrom.getName() + ", " + sectionFrom.getName());
                tripDto.setToQuestion(townTo.getName() + ", " + areaTo.getName() + ", " + sectionTo.getName());
                tripDto.setTaxiPrice(trip.getPrice());
                tripDto.setTripId(trip.getTrip_id());
                if (taxilocation != null) {
                    tripDto.setTaxiLocation(taxilocation.getName());
                    tripDto.setLocationId(taxilocation.getLocation_id());
                } else {
                    tripDto.setTaxiLocation("Unknown Location");
//
                }

                adminTripDtos.add(tripDto);
            }
        }

        return adminTripDtos;
        }

        public TripLocationDto updateTripAdmin(Long tripId, Long locationId,TripLocationDto tripLocation) {
            Location location = locationRepository.findById(locationId).get();
            Trip trip = tripRepository.findById(tripId).get();

            Location locationTemp = new Location();
            Trip tripTemp = new Trip();
            TripLocationDto tripLocationDto = new TripLocationDto();

            if(location.getName() != null && !(location.getName()
                    .equals(tripLocation.getLocation().getName()))) {
                locationTemp.setName(tripLocation.getLocation().getName());
            }
            if(location.getLatitude() != null && !(location.getLatitude()
                    .equals(tripLocationDto.getLocation().getLatitude()))) {
                locationTemp.setLatitude(tripLocationDto.getLocation().getLatitude());
            }
            if(location.getLongitude() != null && !(location.getLongitude()
                    .equals(tripLocationDto.getLocation().getLongitude()))) {
                locationTemp.setLongitude(tripLocationDto.getLocation().getLongitude());
            }
            if(trip.getAttachment() != null && !(trip.getAttachment()
                    .equals(tripLocationDto.getTrip().getAttachment()))) {
                tripTemp.setAttachment(tripLocationDto.getTrip().getAttachment());
            }
            if(trip.getPrice() != null && !(trip.getPrice()
                    .equals(tripLocationDto.getTrip().getPrice()))) {
                tripTemp.setPrice(tripLocationDto.getTrip().getPrice());
            }
            if(trip.getNote() != null && !(trip.getNote().equals(tripLocationDto.getTrip().getNote()))) {
                tripTemp.setPrice(tripLocationDto.getTrip().getPrice());
            }

            tripLocationDto.setLocation(locationTemp);
            tripLocationDto.setTrip(tripTemp);
            return tripLocationDto;
        }

        public void deleteTripLocation(Long tripId, Long locationId) {
            locationRepository.deleteById(locationId);
            tripRepository.deleteById(tripId );
        }


    }
