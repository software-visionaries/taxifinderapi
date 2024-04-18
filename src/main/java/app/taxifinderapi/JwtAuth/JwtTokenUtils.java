package app.taxifinderapi.JwtAuth;

import java.time.Instant;
import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import app.taxifinderapi.Config.UserInfoConfig;
import app.taxifinderapi.repository.UserRepository;

@Component
public class JwtTokenUtils {

    private final UserRepository userRepository;

    public JwtTokenUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserName(Jwt jwtToken){
        return jwtToken.getSubject();
    }

    public boolean isTokenValid(Jwt jwtToken, UserDetails userDetails){
        final String userName = getUserName(jwtToken);
        boolean isTokenExpired = getIfTokenIsExpired(jwtToken);
        boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getUsername());
        return !isTokenExpired && isTokenUserSameAsDatabase;
    }

    private boolean getIfTokenIsExpired(Jwt jwtToken){
        return Objects.requireNonNull(jwtToken.getExpiresAt()).isBefore(Instant.now());
    }
    
    public UserDetails userDetails(String email){
        return userRepository.findByEmail(email).map(UserInfoConfig::new).orElseThrow(() -> new UsernameNotFoundException(email + " does not exist"));
    }

}
