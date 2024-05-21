package app.taxifinderapi.service;

import app.taxifinderapi.dto.AdminQuestionDto;
import app.taxifinderapi.dto.NotificationDto;
import app.taxifinderapi.dto.TownAreaSectionDto;
import app.taxifinderapi.model.*;
import app.taxifinderapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import app.payload.request.QuestionRequest;
import app.taxifinderapi.dto.QuestionDTO;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    FromQuestionRepository fromQuestionRepository;

    @Autowired
    ToQuestionRepository toQuestionRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TownRepository townRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    SectionRepository sectionRepository;

    public QuestionDTO getQuestion(Long question_id){
        Question question = questionRepository.findById(question_id).orElse(null);
        QuestionDTO questionDTO = new QuestionDTO(question.getQuestionId(), 
        question.getFromQuestion().getAddress().getTown().getName(), 
        question.getFromQuestion().getAddress().getArea().getName(), 
        question.getFromQuestion().getAddress().getSection().getName(), 
        question.getToQuestion().getAddress().getTown().getName(), 
        question.getToQuestion().getAddress().getArea().getName(), 
        question.getToQuestion().getAddress().getSection().getName());
        return questionDTO;
    }

    @Transactional
    public ResponseEntity<String> postQuestion(Long user_id, QuestionRequest question) {

        User user = userRepository.findById(user_id).orElse(null);

        Town fromTown = new Town(word(question.getFromTown()));
        townRepository.save(fromTown);

        Town toTown = new Town(word(question.getToTown()));
        townRepository.save(toTown);

        Area fromArea = new Area(word(question.getFromArea()));
        fromArea.setTown(fromTown);
        areaRepository.save(fromArea);

        Area toArea = new Area(word(question.getToArea()));
        toArea.setTown(toTown);
        areaRepository.save(toArea);

        Section fromSection = new Section(question.getFromSection());
        fromSection.setArea(fromArea);
        sectionRepository.save(fromSection);

        Section toSection = new Section(question.getToSection());
        toSection.setArea(toArea);
        sectionRepository.save(toSection);

        Address addressFrom = new Address(fromTown, fromArea, fromSection);
        addressFrom.setUser(user);
        addressFrom.setTown(fromTown);
        addressFrom.setArea(fromArea);
        addressFrom.setSection(fromSection);
        addressRepository.save(addressFrom);

        Address addressTo = new Address(toTown, toArea, toSection);
        addressTo.setUser(user);
        addressTo.setTown(toTown);
        addressTo.setArea(toArea);
        addressTo.setSection(toSection);
        addressRepository.save(addressTo);

        ToQuestion toQuestion = new ToQuestion(addressTo);
        toQuestion.setAddress(addressTo);
        toQuestionRepository.save(toQuestion);

        FromQuestion fromQuestion = new FromQuestion(addressFrom);
        fromQuestion.setAddress(addressFrom);
        fromQuestionRepository.save(fromQuestion);

        Question userQuestion = new Question(fromQuestion, toQuestion);
        userQuestion.setFromQuestion(fromQuestion);
        userQuestion.setToQuestion(toQuestion);

        if (user != null) {
            userQuestion.setUser(user);
            questionRepository.save(userQuestion);
            return ResponseEntity.ok().body("Question was added successfully");
        }

        return ResponseEntity.badRequest().body("Question failed to be added");
    }

    public String word(String paragraph) {
        char separator = ',';
        String paragraphToLowerCase = paragraph.toLowerCase();
        StringBuilder word = new StringBuilder();
        String place = null;
        boolean foundComma = false;

        for (char character : paragraphToLowerCase.toCharArray()) {
            if (isSeparator(character, separator)) {
                if (word.length() > 0) {
                    place = capitalizeFirstLetter(word.toString());
                    break;
                }
                foundComma = true;
            } else {
                if (!foundComma) {
                    word.append(character);
                }
            }
        }

        if (!foundComma && word.length() > 0) {
            place = capitalizeFirstLetter(word.toString());
        }

        return place;
    }
    
    public static boolean isSeparator(char character, char separator) {
        return character == separator;
    }
    public static String capitalizeFirstLetter(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    public List<AdminQuestionDto> findAllAdminQuestion() {
        List<Question> questions = questionRepository.findAll();


        Map<Long, FromQuestion> fromQuestionMap = new HashMap<>();
        Map<Long, ToQuestion> toQuestionMap = new HashMap<>();
        Map<Long, Address> addressMap = new HashMap<>();
        Map<Long, Town> townMap = new HashMap<>();
        Map<Long, Area> areaMap = new HashMap<>();
        Map<Long, Section> sectionMap = new HashMap<>();


        fromQuestionRepository.findAll().forEach(fromQuestion -> fromQuestionMap.put(fromQuestion.getId(), fromQuestion));
        toQuestionRepository.findAll().forEach(toQuestion -> toQuestionMap.put(toQuestion.getId(), toQuestion));
        addressRepository.findAll().forEach(address -> addressMap.put(address.getId(), address));
        townRepository.findAll().forEach(town -> townMap.put(town.getId(), town));
        areaRepository.findAll().forEach(area -> areaMap.put(area.getId(), area));
        sectionRepository.findAll().forEach(section -> sectionMap.put(section.getId(), section));

        List<AdminQuestionDto> adminQuestionDtos = new ArrayList<>();


        for (Question question : questions) {
            AdminQuestionDto adminQuestionDto = new AdminQuestionDto();


            FromQuestion fromQuestion = fromQuestionMap.getOrDefault(question.getFromQuestion().getId(), null);
            ToQuestion toQuestion = toQuestionMap.getOrDefault(question.getToQuestion().getId(), null);
            Address addressTo = (toQuestion != null) ? addressMap.getOrDefault(toQuestion.getAddress().getId(), null) : null;
            Address addressFrom = (fromQuestion != null) ? addressMap.getOrDefault(fromQuestion.getAddress().getId(), null) : null;
            Town townTo = (addressTo != null) ? townMap.getOrDefault(addressTo.getTown().getId(), null) : null;
            Town townFrom = (addressFrom != null) ? townMap.getOrDefault(addressFrom.getTown().getId(), null) : null;
            Area areaTo = null;
            Area areaFrom = null;
            Section sectionTo = null;
            Section sectionFrom = null;
            String status = "Unanswered";


            if (townTo != null && townFrom != null) {
                areaTo = findArea(townTo.getAreas(), areaMap);
                areaFrom = findArea(townFrom.getAreas(), areaMap);

                if (areaTo != null && areaFrom != null) {
                    sectionTo = findSection(areaTo.getSections(), sectionMap);
                    sectionFrom = findSection(areaFrom.getSections(), sectionMap);
                }
            }


            if (question.getTrips().stream().anyMatch(trip -> trip != null)) {
                status = "Answered";
            }


            adminQuestionDto.setFromQuestion((townFrom != null && areaFrom != null && sectionFrom != null) ?
                    townFrom.getName() + ", " + areaFrom.getName() + ", " + sectionFrom.getName() : "");
            adminQuestionDto.setToQuestion((townTo != null && areaTo != null && sectionTo != null) ?
                    townTo.getName() + ", " + areaTo.getName() + ", " + sectionTo.getName() : "");
            adminQuestionDto.setStatus(status);
            adminQuestionDto.setAreaId(areaFrom.getId());
            adminQuestionDto.setTownId(townFrom.getId());
            adminQuestionDto.setSectionId(sectionFrom.getId());
            adminQuestionDto.setTownToId(townTo.getId());
            adminQuestionDto.setAreaToId(areaTo.getId());
            adminQuestionDto.setSectionToId(sectionTo.getId());

            adminQuestionDtos.add(adminQuestionDto);
        }

        return adminQuestionDtos;
    }


    private Area findArea(List<Area> areas, Map<Long, Area> areaMap) {
        for (Area area : areas) {
            if (areaMap.containsKey(area.getId())) {
                return areaMap.get(area.getId());
            }
        }
        return null;
    }


    private Section findSection(List<Section> sections, Map<Long, Section> sectionMap) {
        for (Section section : sections) {
            if (sectionMap.containsKey(section.getId())) {
                return sectionMap.get(section.getId());
            }
        }
        return null;
    }


    public TownAreaSectionDto updateQuestionAdmin(Long fromTownId, Long toTownId, Long fromArea, Long toArea,
                                                  Long fromSection, Long toSection, TownAreaSectionDto townAreaSectionDto) {
        Town fromTownTemp = townRepository.findById(fromTownId).orElseThrow(() -> new RuntimeException("From town not found"));
        Town toTownTemp = townRepository.findById(toTownId).orElseThrow(() -> new RuntimeException("To town not found"));
        Area fromAreaTemp = areaRepository.findById(fromArea).orElseThrow(() -> new RuntimeException("From area not found"));
        Area toAreaTemp = areaRepository.findById(toArea).orElseThrow(() -> new RuntimeException("To area not found"));
        Section fromSectionTemp = sectionRepository.findById(fromSection).orElseThrow(() -> new RuntimeException("From section not found"));
        Section toSectionTemp = sectionRepository.findById(toSection).orElseThrow(() -> new RuntimeException("To section not found"));

        if (!fromTownTemp.equals(toTownTemp)) {
            if (townAreaSectionDto.getFromTown() != null && townAreaSectionDto.getFromTown().getName() != null &&
                    !townAreaSectionDto.getFromTown().getName().equals(fromTownTemp.getName())) {
                fromTownTemp.setName(townAreaSectionDto.getFromTown().getName());
                townRepository.save(fromTownTemp);
            }
            if (townAreaSectionDto.getToTown() != null && townAreaSectionDto.getToTown().getName() != null &&
                    !townAreaSectionDto.getToTown().getName().equals(toTownTemp.getName())) {
                toTownTemp.setName(townAreaSectionDto.getToTown().getName());
                townRepository.save(toTownTemp);
            }
        }

        if (!toAreaTemp.equals(fromAreaTemp)) {
            if (townAreaSectionDto.getFromArea() != null && townAreaSectionDto.getFromArea().getName() != null &&
                    !townAreaSectionDto.getFromArea().getName().equals(fromAreaTemp.getName())) {
                fromAreaTemp.setName(townAreaSectionDto.getFromArea().getName());
                areaRepository.save(fromAreaTemp);
            }
            if (townAreaSectionDto.getToArea() != null && townAreaSectionDto.getToArea().getName() != null &&
                    !townAreaSectionDto.getToArea().getName().equals(toAreaTemp.getName())) {
                toAreaTemp.setName(townAreaSectionDto.getToArea().getName());
                areaRepository.save(toAreaTemp);
            }
        }

        if (!toSectionTemp.equals(fromSectionTemp)) {
            if (townAreaSectionDto.getFromSection() != null && townAreaSectionDto.getFromSection().getName() != null &&
                    !townAreaSectionDto.getFromSection().getName().equals(fromSectionTemp.getName())) {
                fromSectionTemp.setName(townAreaSectionDto.getFromSection().getName());
                sectionRepository.save(fromSectionTemp);
            }
            if (townAreaSectionDto.getToSection() != null && townAreaSectionDto.getToSection().getName() != null &&
                    !townAreaSectionDto.getToSection().getName().equals(toSectionTemp.getName())) {
                toSectionTemp.setName(townAreaSectionDto.getToSection().getName());
                sectionRepository.save(toSectionTemp);
            }
        }

        TownAreaSectionDto townAreaSectionDtoHolder = new TownAreaSectionDto();
        townAreaSectionDtoHolder.setFromArea(fromAreaTemp);
        townAreaSectionDtoHolder.setToArea(toAreaTemp);
        townAreaSectionDtoHolder.setFromTown(fromTownTemp);
        townAreaSectionDtoHolder.setToTown(toTownTemp);
        townAreaSectionDtoHolder.setFromSection(fromSectionTemp);
        townAreaSectionDtoHolder.setToSection(toSectionTemp);
        return townAreaSectionDtoHolder;
    }


    public void deleteTownAreaSection (Long fromTownId, Long toTownId, Long fromAreaId, Long toAreaId,
                                       Long fromSectionId, Long toSectionId) {
         townRepository.deleteById(fromTownId);
         townRepository.deleteById(toTownId);
         areaRepository.deleteById(fromAreaId);
         areaRepository.deleteById(toAreaId);
         sectionRepository.deleteById(fromSectionId);
         sectionRepository.deleteById(toSectionId);

    }


    public List<NotificationDto> displayNotification() {
        List<User> users = userRepository.findAll();
        List<Question> questions = questionRepository.findAll();
        List<NotificationDto> unAnsweredQuestions = new ArrayList<>();

        for (User user : users) {
            List<Address> userAddresses = user.getAddresses();
            boolean hasQuestionToAnswer = false;

            for (Question question : questions) {
                if (question.getTrips() == null) {
                    boolean questionMatched = false;
                    for (Address userAddress : userAddresses) {
                        if (userAddress.getTown().equals(question.getFromQuestion().getAddress().getTown()) &&
                                userAddress.getArea().equals(question.getFromQuestion().getAddress().getArea())) {

                            NotificationDto notificationDto = new NotificationDto();
                            notificationDto.setMessage("Hey Short-lefts! Where can I get taxis from " +
                                    question.getFromQuestion().getAddress().getTown().getName() + " " +
                                    question.getFromQuestion().getAddress().getArea().getName() + " " +
                                    question.getFromQuestion().getAddress().getSection().getName() + " " +
                                    question.getFromQuestion().getAddress().getSection().getNumber() + " to " +
                                    question.getToQuestion().getAddress().getTown().getName() + " " +
                                    question.getToQuestion().getAddress().getArea().getName() + " " +
                                    question.getToQuestion().getAddress().getSection().getName() + " " +
                                    question.getToQuestion().getAddress().getSection().getNumber() + "?");
                            notificationDto.setQuestionId(question.getQuestionId());
                            unAnsweredQuestions.add(notificationDto);
                            questionMatched = true;
                            hasQuestionToAnswer = true;
                            break;
                        }
                    }
                    if (!questionMatched) {
                        break;
                    }
                }
            }

            if (!hasQuestionToAnswer) {
                NotificationDto notificationDto = new NotificationDto();
                notificationDto.setMessage("no Questions for region to answer");
                unAnsweredQuestions.add(notificationDto);
            }
        }

        return unAnsweredQuestions;
    }


}
