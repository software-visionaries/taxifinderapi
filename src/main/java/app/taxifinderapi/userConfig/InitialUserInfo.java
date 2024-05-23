//package app.taxifinderapi.userConfig;
//
//import java.util.List;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import app.taxifinderapi.model.User;
//import app.taxifinderapi.repository.UserRepository;
//
//@Component
//public class InitialUserInfo implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    public InitialUserInfo(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        User maneger = new User();
//        maneger.setName("Manager");
//        maneger.setPassword(passwordEncoder.encode("Manager@1"));
//        maneger.setRoles("ROLE_ADMIN");
//        maneger.setEmail("manager@shortleft.com");
//
//        User user = new User();
//        user.setName("User");
//        user.setPassword(passwordEncoder.encode("User@1"));
//        user.setRoles("ROLE_USER");
//        user.setEmail("user@shortleft.com");
//
//        userRepository.saveAll(List.of(maneger, user));
//    }
//}
