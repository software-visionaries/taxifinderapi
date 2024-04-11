package app.taxifinderapi.dto;

public class QuestionDTO {

    private Long question_id;
    private String fromTownName;
    private String fromAreaName;
    private String fromSectionName;
    private String toTownName;
    private String toAreaName;
    private String toSectionName;

    public QuestionDTO() {
    }

    public QuestionDTO(Long question_id, String fromTownName, String fromAreaName, String fromSectionName,
            String toTownName, String toAreaName, String toSectionName) {
        this.question_id = question_id;
        this.fromTownName = fromTownName;
        this.fromAreaName = fromAreaName;
        this.fromSectionName = fromSectionName;
        this.toTownName = toTownName;
        this.toAreaName = toAreaName;
        this.toSectionName = toSectionName;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public String getFromTownName() {
        return fromTownName;
    }

    public void setFromTownName(String fromTownName) {
        this.fromTownName = fromTownName;
    }

    public String getFromAreaName() {
        return fromAreaName;
    }

    public void setFromAreaName(String fromAreaName) {
        this.fromAreaName = fromAreaName;
    }

    public String getFromSectionName() {
        return fromSectionName;
    }

    public void setFromSectionName(String fromSectionName) {
        this.fromSectionName = fromSectionName;
    }

    public String getToTownName() {
        return toTownName;
    }

    public void setToTownName(String toTownName) {
        this.toTownName = toTownName;
    }

    public String getToAreaName() {
        return toAreaName;
    }

    public void setToAreaName(String toAreaName) {
        this.toAreaName = toAreaName;
    }

    public String getToSectionName() {
        return toSectionName;
    }

    public void setToSectionName(String toSectionName) {
        this.toSectionName = toSectionName;
    }
    
}
