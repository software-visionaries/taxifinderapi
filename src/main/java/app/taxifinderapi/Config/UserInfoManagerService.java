package app.taxifinderapi.Config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.taxifinderapi.repository.UserRepository;

@Service
public class UserInfoManagerService implements UserDetailsService {

    private final UserRepository userRepository;
    
    public UserInfoManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).map(UserInfoConfig::new)
        .orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + email + "does not exist"));
    }
}
