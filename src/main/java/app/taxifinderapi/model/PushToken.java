package app.taxifinderapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PushToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long push_token_id;

    private String push_token_value;

    @ManyToOne()
    @JoinColumn(name = "user")
    private User user;

    public PushToken() {
    }

    public PushToken(String push_token_value) {
        this.push_token_value = push_token_value;
    }

    public Long getPush_token_id() {
        return push_token_id;
    }

    public void setPush_token_id(Long push_token_id) {
        this.push_token_id = push_token_id;
    }

    public String getPush_token_value() {
        return push_token_value;
    }

    public void setPush_token_value(String push_token_value) {
        this.push_token_value = push_token_value;
    }

    public void setUser(User user){
        this.user = user;
    }

}
