package app.taxifinderapi.dto;

import app.taxifinderapi.model.ToQuestion;

public class AdminQuestionDto {
    private String fromQuestion;
    private String ToQuestion;
    private String status;
    private Long townId;
    private Long AreaId;
    private Long SectionId;
    private Long townToId;
    private Long AreaToId;
    private Long SectionToId;

    public AdminQuestionDto() {
    }

    public Long getTownToId() {
        return townToId;
    }

    public void setTownToId(Long townToId) {
        this.townToId = townToId;
    }

    public Long getAreaToId() {
        return AreaToId;
    }

    public void setAreaToId(Long areaToId) {
        AreaToId = areaToId;
    }

    public Long getSectionToId() {
        return SectionToId;
    }

    public void setSectionToId(Long sectionToId) {
        SectionToId = sectionToId;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public Long getAreaId() {
        return AreaId;
    }

    public void setAreaId(Long areaId) {
        AreaId = areaId;
    }

    public Long getSectionId() {
        return SectionId;
    }

    public void setSectionId(Long sectionId) {
        SectionId = sectionId;
    }

    public String getFromQuestion() {
        return fromQuestion;
    }

    public void setFromQuestion(String fromQuestion) {
        this.fromQuestion = fromQuestion;
    }

    public String getToQuestion() {
        return ToQuestion;
    }

    public void setToQuestion(String toQuestion) {
        ToQuestion = toQuestion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
