package app.taxifinderapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.payload.request.QuestionRequest;
import app.taxifinderapi.service.QuestionService;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;
    
    @PostMapping("/add/question/{user_id}")
    public ResponseEntity<String> addQuestion(@PathVariable Long user_id,@RequestBody QuestionRequest questionRequest){
        return questionService.postQuestion(user_id, questionRequest);
    }
}
