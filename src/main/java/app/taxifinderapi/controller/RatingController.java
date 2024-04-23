package app.taxifinderapi.controller;

import app.taxifinderapi.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping(value = "/vote/{userId}/{tripId}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<String> vote(@PathVariable Long userId, @PathVariable Long tripId, @RequestParam String action) {
        if ("upvote".equals(action)) {
            ratingService.upVote(userId, tripId,action);
            return ResponseEntity.ok("Upvoted successfully");
        } else if ("downvote".equals(action)) {
            ratingService.downVote(userId, tripId,action);
            return ResponseEntity.ok("Downvoted successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid action specified");
        }
    }
    @GetMapping("like/{tripId}")
    public Integer totalUpVote(@PathVariable Long tripId) {
        return ratingService.totalUpVote(tripId);
    }
    @GetMapping("dislike/{tripId}")
    public Integer totalDownVote(@PathVariable Long tripId) {
        return ratingService.totalDownVote(tripId);
    }

}
