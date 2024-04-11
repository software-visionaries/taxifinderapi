package app.taxifinderapi.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReplyCommentDto {
    private String replayCommentMessage;
    private String replayCommentOwner;
    private String replyCommentDuration;

    public ReplyCommentDto(String replayCommentMessage, String replayCommentOwner, String replyCommentDuration) {
        this.replayCommentMessage = replayCommentMessage;
        this.replayCommentOwner = replayCommentOwner;
        this.replyCommentDuration = replyCommentDuration;
    }

    public String getReplyCommentDuration() {
        return replyCommentDuration;
    }

    public void setReplyCommentDuration(String replyCommentDuration) {
        this.replyCommentDuration = replyCommentDuration;
    }

    public ReplyCommentDto() {
    }

    public String getReplayCommentMessage() {
        return replayCommentMessage;
    }

    public void setReplayCommentMessage(String replayCommentMessage) {
        this.replayCommentMessage = replayCommentMessage;
    }

    public String getReplayCommentOwner() {
        return replayCommentOwner;
    }

    public void setReplayCommentOwner(String replayCommentOwner) {
        this.replayCommentOwner = replayCommentOwner;
    }
}
