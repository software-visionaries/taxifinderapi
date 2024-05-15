package app.taxifinderapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.payload.request.UserRequest;
import app.taxifinderapi.dto.UserResponseDto;
import app.taxifinderapi.model.PushToken;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.PushTokenRepository;
import app.taxifinderapi.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PushTokenRepository pushTokenRepository;

    @Transactional
    public UserResponseDto addUser(UserRequest userRequest) {
        User addedUser = userRepository
                .save(new User(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword()));
        PushToken pushToken = new PushToken();
        pushToken.setPush_token_value(userRequest.getPushToken());
        pushToken.setUser(addedUser);
        pushTokenRepository.save(pushToken);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUser_id(addedUser.getUser_id());
        userResponseDto.setName(addedUser.getName());
        userResponseDto.setEmail(addedUser.getEmail());
        userResponseDto.setPushToken(pushToken.getPush_token_value());
        return userResponseDto;
    }
}
