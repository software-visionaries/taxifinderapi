package app.taxifinderapi.service;

import app.taxifinderapi.dto.CommentAndReplyCommentDto;
import app.taxifinderapi.dto.CommentDto;
import app.taxifinderapi.dto.ReplyCommentDto;
import app.taxifinderapi.exceptions.CommentException;
import app.taxifinderapi.exceptions.TripException;
import app.taxifinderapi.exceptions.UserException;
import app.taxifinderapi.model.Comment;
import app.taxifinderapi.model.ReplyComment;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.CommentRepository;
import app.taxifinderapi.repository.ReplyCommentRepository;
import app.taxifinderapi.repository.TripRepository;
import app.taxifinderapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReplyCommentRepository replyCommentRepository;


    public void createComment(Long tripId, String message, Long userId) {

        Trip findTrip = tripRepository.findById(tripId).orElseGet( () -> {
            throw new TripException("Taxi rank or Taxi pick point not found");
        });

        User findUser = userRepository.findById(userId).orElseThrow( () -> {
            throw new UserException("User not found");
        });
        Comment commentHolder = new Comment();
        if(message.length() > 0 ) {
            commentHolder.setTrip(findTrip);
            commentHolder.setUser(findUser);
            commentHolder.setMessage(message);
            commentHolder.setCreatedDate(LocalDate.now());
            commentHolder.setCreatedTime(LocalTime.now());
        }
        commentRepository.save(commentHolder);
    }

    public void createReplyComment(String message, Long userId,Long commentId) {

        User findUser = userRepository.findById(userId).orElseThrow( () -> {
            throw new UserException("User not found");
        });

        Comment findComment = commentRepository.findById(commentId).orElseThrow( () -> {
            throw new CommentException("Comment not Found");
        });
        ReplyComment replyComment = new ReplyComment();
        if(message.length() > 0) {
            replyComment.setUser(findUser);
            replyComment.setMessage(message);
            replyComment.setCreatedDate(LocalDate.now());
            replyComment.setCreatedTime(LocalTime.now());
            replyComment.setComment(findComment);
        }
        replyCommentRepository.save(replyComment);
    }
    public String getTimeAgo(LocalDate createdDate, LocalTime createdTime) {
        LocalDateTime commentDateTime = LocalDateTime.of(createdDate, createdTime);
        LocalDateTime currentDateTime = LocalDateTime.now();

        Duration duration = Duration.between(commentDateTime, currentDateTime);
        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return formatTimeUnit(seconds, "second");
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            return formatTimeUnit(minutes, "minute") + " and " + formatTimeUnit(remainingSeconds, "second");
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            long remainingMinutes = (seconds % 3600) / 60;
            return formatTimeUnit(hours, "hour") + " and " + formatTimeUnit(remainingMinutes, "minute");
        } else if (seconds < 2592000) {
            long days = seconds / 86400;
            return formatTimeUnit(days, "day");
        } else if (seconds < 31536000) {
            long months = seconds / 2592000;
            long remainingDays = (seconds % 2592000) / 86400;
            return formatTimeUnit(months, "month") + " and " + formatTimeUnit(remainingDays, "day");
        } else {
            long years = seconds / 31536000;
            long remainingMonths = (seconds % 31536000) / 2592000;
            return formatTimeUnit(years, "year") + " and " + formatTimeUnit(remainingMonths, "month");
        }
    }

    private String formatTimeUnit(long value, String unit) {
        if (value == 1) {
            return value + " " + unit;
        } else {
            return value + " " + unit + "s";
        }
    }

    public List<CommentAndReplyCommentDto> commentsAndReplyComments(Long tripId) {
        Trip findTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripException("Trip doesn't exist"));

        List<CommentAndReplyCommentDto> commentDtos = commentRepository.findCommentsByTrip(findTrip).stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto(
                            comment.getMessage(),
                            comment.getUser().getName(),
                            getTimeAgo(comment.getCreatedDate(), comment.getCreatedTime())
                    );

                    List<ReplyCommentDto> replyCommentDtos = replyCommentRepository.findByComment(comment).stream()
                            .map(replyComment -> new ReplyCommentDto(
                                    replyComment.getMessage(),
                                    getTimeAgo(replyComment.getCreatedDate(), replyComment.getCreatedTime()),
                                    replyComment.getUser().getName()
                            ))
                            .sorted(Comparator.comparing((ReplyCommentDto replayDto)->replayDto.getReplyCommentDuration()).reversed())
                            .collect(Collectors.toList());

                    return new CommentAndReplyCommentDto(commentDto, replyCommentDtos);
                })
                .sorted(Comparator.comparing((CommentAndReplyCommentDto dto) -> dto.getCommentDto().getDuration())
                        .reversed())
                .collect(Collectors.toList());

        return commentDtos;
    }


}
