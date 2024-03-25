package app.taxifinderapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import app.payload.request.QuestionRequest;
import app.taxifinderapi.dto.QuestionDTO;
import app.taxifinderapi.model.Address;
import app.taxifinderapi.model.Area;
import app.taxifinderapi.model.FromQuestion;
import app.taxifinderapi.model.Question;
import app.taxifinderapi.model.Section;
import app.taxifinderapi.model.ToQuestion;
import app.taxifinderapi.model.Town;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.AddressRepository;
import app.taxifinderapi.repository.AreaRepository;
import app.taxifinderapi.repository.FromQuestionRepository;
import app.taxifinderapi.repository.QuestionRepository;
import app.taxifinderapi.repository.SectionRepository;
import app.taxifinderapi.repository.ToQuestionRepository;
import app.taxifinderapi.repository.TownRepository;
import app.taxifinderapi.repository.UserRepository;
import jakarta.transaction.Transactional;

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

        Town fromTown = new Town(question.getFromTown());
        townRepository.save(fromTown);

        Town toTown = new Town(question.getToTown());
        townRepository.save(toTown);

        Area fromArea = new Area(question.getFromArea());
        fromArea.setTown(fromTown);
        areaRepository.save(fromArea);

        Area toArea = new Area(question.getToArea());
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
}
