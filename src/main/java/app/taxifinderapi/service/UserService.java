package app.taxifinderapi.service;

// import org.hibernate.mapping.List;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
    return userRepository.findAll();
    }

    @SuppressWarnings("null")
    public Optional<User> findByuser_id(Long user_id) {
       return userRepository.findById(user_id);
    }
    
}
