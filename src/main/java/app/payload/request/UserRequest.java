package app.payload.request;

public class UserRequest {

    private String name;
    private String email;
    private String password;
    private String pushToken;

    public UserRequest(String name, String email, String password, String pushToken) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.pushToken = pushToken;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }
}
