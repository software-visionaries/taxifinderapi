package app.taxifinderapi.controller;

import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/add/user")
    public User addUser(@RequestBody User user){
        User currUser = new User(user.getName(), user.getEmail(), user.getPassword());
        return userRepository.save(currUser);
    }
    @GetMapping("/users/{id}")
    public User getUser (@PathVariable Long id) {
     return    userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("user does nt exists");
        });
    }


}
