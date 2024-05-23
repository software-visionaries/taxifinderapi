package app.taxifinderapi.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.taxifinderapi.dto.UserRegistrationDto;
import app.taxifinderapi.model.User;

@Component
public class UserInfoMapper {
    
    private final PasswordEncoder passwordEncoder;

    public UserInfoMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User convertToEntity(UserRegistrationDto userRegistrationDto){
        User user = new User();
        user.setEmail(userRegistrationDto.email());
        user.setName(userRegistrationDto.name());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.password()));
        user.setRoles(userRegistrationDto.role());
        return user;
    }
}
