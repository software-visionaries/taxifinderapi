package app.taxifinderapi.controller;

import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.UserRepository;
import app.taxifinderapi.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/sign-up")


public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
     private UserService userService;

    @PostMapping("/add/user")
    public User addUser(@RequestBody User user){
      return userService.saveUser(user);
    }

    @GetMapping("/User/All")
    public List<User> getAllusers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/User/{user_id}")
    public Optional<User> getUser(@PathVariable Long user_id) {
        return userService.findByuser_id(user_id);
    }
    
}
