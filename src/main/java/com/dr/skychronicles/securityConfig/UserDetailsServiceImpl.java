package com.dr.skychronicles.securityConfig;

import com.dr.skychronicles.entity.User;
import com.dr.skychronicles.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.getUserByEmail(email));
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new MyUserDetails(user);
    }
}
