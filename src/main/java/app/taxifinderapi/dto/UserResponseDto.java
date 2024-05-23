package app.taxifinderapi.dto;

import java.util.List;

import app.taxifinderapi.model.PushToken;

public class UserResponseDto {

    private Long user_id;
    private String name;
    private String email;
    private List<PushToken> pushToken;

    public UserResponseDto() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PushToken> getPushToken() {
        return pushToken;
    }

    public void setPushToken(List<PushToken> pushToken) {
        this.pushToken = pushToken;
    }

}
