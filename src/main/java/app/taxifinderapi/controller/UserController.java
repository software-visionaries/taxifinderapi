package app.taxifinderapi.controller;

import app.payload.request.UserRequest;
import app.taxifinderapi.dto.UserResponseDto;
import app.taxifinderapi.repository.UserRepository;
import app.taxifinderapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping("/add/user")
    public UserResponseDto addUser(@RequestBody UserRequest user){
        return userService.addUser(user);
    }


}
