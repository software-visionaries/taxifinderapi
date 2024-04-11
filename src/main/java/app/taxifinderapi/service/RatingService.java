package app.taxifinderapi.service;

import app.taxifinderapi.exceptions.TripException;
import app.taxifinderapi.exceptions.UserException;
import app.taxifinderapi.model.Rating;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.RatingRepository;
import app.taxifinderapi.repository.TripRepository;
import app.taxifinderapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RatingRepository ratingRepository;


    public void upVote(Long userId, Long tripId,String action) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripException("Trip doesn't exist"));

        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));

        Rating existingRating = ratingRepository.findByUser(user);

        if (existingRating != null) {
            if (existingRating.isUpVoted() && action.equals("upvote")) {
                existingRating.setUpVoted(false);
                existingRating.setDownVoted(false);
                existingRating.setUpVote(0);
                ratingRepository.save(existingRating);
            } else if(!existingRating.isUpVoted() && action.equals("upvote")){
                existingRating.setUpVoted(true);
                existingRating.setDownVoted(false);
                existingRating.setUpVote(1);
                existingRating.setDownVote(0);
                ratingRepository.save(existingRating);
            } else if (existingRating.isDownVoted() && action.equals("upvote")) {
                existingRating.setUpVoted(true);
                existingRating.setDownVoted(false);
                existingRating.setUpVote(1);
                existingRating.setDownVote(0);
                ratingRepository.save(existingRating);
            }
        } else {
            Rating newRating = new Rating();
            newRating.setUser(user);
            newRating.setTrip(trip);
            newRating.setUpVoted(true);
            newRating.setDownVoted(false);
            newRating.setUpVote(1);
            ratingRepository.save(newRating);
        }
    }
    public void downVote(Long userId, Long tripId, String action) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripException("Trip doesn't exist"));

        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));

        Rating existingRating = ratingRepository.findByUser(user);

        if (existingRating != null) {
            if (existingRating.isDownVoted() && action.equals("downvote")) {
                existingRating.setUpVoted(false);
                existingRating.setDownVoted(false);
                existingRating.setDownVote(0);
                ratingRepository.save(existingRating);
            } else if (!existingRating.isDownVoted() && action.equals("downvote")) {
                existingRating.setUpVoted(false);
                existingRating.setDownVoted(true);
                existingRating.setUpVote(0);
                existingRating.setDownVote(1);
                ratingRepository.save(existingRating);
            } else if (existingRating.isUpVoted() && action.equals("downvote")) {
                existingRating.setUpVoted(false);
                existingRating.setDownVoted(true);
                existingRating.setUpVote(0);
                existingRating.setDownVote(1);
                ratingRepository.save(existingRating);
            }
        } else {
            Rating newRating = new Rating();
            newRating.setUser(user);
            newRating.setTrip(trip);
            newRating.setUpVoted(false);
            newRating.setDownVoted(true);
            newRating.setDownVote(1);
            ratingRepository.save(newRating);
        }
    }

    public Integer totalUpVote(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripException("Trip doesn't exist"));

        List<Rating> ratings = ratingRepository.findAllByTrip(trip);

        return ratings.stream().mapToInt(Rating::getUpVote).sum();
    }
    public Integer totalDownVote(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripException("Trip doesn't exist"));

        List<Rating> ratings = ratingRepository.findAllByTrip(trip);

        return ratings.stream().mapToInt(Rating::getDownVote).sum();
    }

}
