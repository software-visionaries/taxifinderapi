package app.taxifinderapi.controller;

import app.taxifinderapi.dto.AdminQuestionDto;
import app.taxifinderapi.dto.NotificationDto;
import app.taxifinderapi.dto.TownAreaSectionDto;
import app.taxifinderapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.payload.request.QuestionRequest;
import app.taxifinderapi.dto.QuestionDTO;
import app.taxifinderapi.service.QuestionService;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/get/question/{question_id}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long question_id){
        QuestionDTO question = questionService.getQuestion(question_id);
        return ResponseEntity.ok().body(question);
    }
    
    @PostMapping("/add/question/{user_id}")
    public ResponseEntity<String> addQuestion(@PathVariable Long user_id,@RequestBody QuestionRequest questionRequest){
        return questionService.postQuestion(user_id, questionRequest);
    }

    @GetMapping("/admin/questions")
    public List<AdminQuestionDto> findAllAdminQuestion() {
        return questionService.findAllAdminQuestion();
    }
    @GetMapping("/get/unanswered-question/{id}")
    public List<NotificationDto> displayUnansweredQuestion(@PathVariable Long id) {
      return  questionService.displayNotification(id);
    }

    @PutMapping("/admin/questions/update/{fromTownId}/{toTownId}/{fromArea}/{toArea}/{fromSection}/{toSection}")
    public TownAreaSectionDto updateQuestionAdmin(@PathVariable Long fromTownId,@PathVariable Long toTownId,@PathVariable Long fromArea,@PathVariable Long toArea,
                                                  @PathVariable Long fromSection,@PathVariable Long toSection,@RequestBody TownAreaSectionDto townAreaSectionDto) {
        return questionService.updateQuestionAdmin(fromTownId,toTownId,fromArea,toArea,fromSection,toSection,townAreaSectionDto);

    }
    @DeleteMapping("/admin/questions/delete/{fromTownId}/{toTownId}/{fromAreaId}/{toAreaId}/{fromSectionId}/{toSectionId}")
    public void deleteTownAreaSection (Long fromTownId, Long toTownId, Long fromAreaId, Long toAreaId,
                                       Long fromSectionId, Long toSectionId) {
        questionService.deleteTownAreaSection(fromTownId,toTownId,fromAreaId,toAreaId,fromSectionId,toSectionId);
    }


}

