package app.taxifinderapi.dto;

public class NotificationDto {
    private String message;
    private Long questionId;

    public NotificationDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
