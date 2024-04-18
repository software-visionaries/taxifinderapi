package app.taxifinderapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RefreshToken {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "REFRESH_TOKEN", nullable = false, length = 10000)
    private String refreshToken;

    @Column(name = "REVOKED")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public Boolean isRevoked(){
        return this.revoked;
    }

    public void setRevoked(Boolean revoke){
        this.revoked = revoke;
    }

    public RefreshToken() {

    }

    private RefreshToken(RefreshTokenBuilder builder){
        this.refreshToken = builder.refreshToken;
        this.revoked = builder.revoked;
        this.user = builder.user;
    }

    public static RefreshTokenBuilder RefreshTokenBuilder(){
        return new RefreshTokenBuilder();
    }

    public static class RefreshTokenBuilder {
        private String refreshToken;
        private boolean revoked;
        private User user;

        private RefreshTokenBuilder(){}

        public RefreshTokenBuilder refreshToken(String refreshToken){
            this.refreshToken = refreshToken;
            return this;
        }

        public RefreshTokenBuilder revoked(boolean revoked){
            this.revoked = revoked;
            return this;
        }

        public RefreshTokenBuilder user(User user){
            this.user = user;
            return this;
        }

        public RefreshToken build(){
            return new RefreshToken(this);
        }
    }

}
