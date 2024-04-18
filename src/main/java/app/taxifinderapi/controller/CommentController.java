package app.taxifinderapi.controller;

import app.taxifinderapi.dto.CommentAndReplyCommentDto;
import app.taxifinderapi.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("comments/api/v1.0.0.1")
public class CommentController {
    @Autowired
    CommentsService commentsService;
    @PostMapping("/create-comment/{tripId}/{userId}")
    public void createComment(@PathVariable Long tripId, @RequestParam(name = "message") String message, @PathVariable Long userId) {
        commentsService.createComment(tripId,message,userId);
    }
    @PostMapping("/create-replay-comment/{userId}/{commentId}")
    public void createReplyComment(@RequestParam(name = "message") String message,@PathVariable Long userId,@PathVariable Long commentId) {
        commentsService.createReplyComment(message,userId,commentId);
    }
    @GetMapping("/comments/{tripId}")
    public List<CommentAndReplyCommentDto> replyComment(@PathVariable Long tripId) {
        return commentsService.commentsAndReplyComments(tripId);
    }
}
