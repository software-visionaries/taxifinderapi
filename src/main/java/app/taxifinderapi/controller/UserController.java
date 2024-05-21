package app.taxifinderapi.controller;

import app.payload.request.UserRequest;
import app.taxifinderapi.dto.UserResponseDto;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.UserRepository;
import app.taxifinderapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/users/{id}")
    public User getUser (@PathVariable Long id) {
     return    userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("user does nt exists");
        });
    }


}
