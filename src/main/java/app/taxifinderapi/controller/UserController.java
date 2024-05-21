package app.taxifinderapi.controller;

import app.payload.request.UserRequest;
import app.taxifinderapi.dto.UserResponseDto;
import app.taxifinderapi.repository.UserRepository;
import app.taxifinderapi.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    // @PostMapping("/add/user")
    // public UserResponseDto addUser(@RequestBody UserRequest user) {
    //     return userService.addUser(user);
    // }

    @PostMapping("/find/users/{town_name}")
    public List<UserResponseDto> findUsersByTown(@PathVariable String town_name) {
        return userService.findUsersByTown(town_name.toUpperCase().replaceAll("\\s+", ""));
    }

}
