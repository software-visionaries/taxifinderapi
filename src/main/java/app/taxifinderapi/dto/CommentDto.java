package app.taxifinderapi.dto;
public class CommentDto {
    private String commentMessage;
    private String  commentOwner;
    private String duration;

    public CommentDto(String commentMessage, String commentOwner, String duration) {
        this.commentMessage = commentMessage;
        this.commentOwner = commentOwner;
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    public CommentDto() {
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public String getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(String commentOwner) {
        this.commentOwner = commentOwner;
    }
}
