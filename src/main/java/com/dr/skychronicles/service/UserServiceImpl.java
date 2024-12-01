package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.User;
import com.dr.skychronicles.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User signUpUser(User user) {
        user.setRole("USER");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user, MultipartFile imageFile, Authentication authentication) {
        User oldUser = userRepository.getUserByEmail(authentication.getName());

        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Конвертация изображения в строку Base64
                String base64Image = Base64.getEncoder().encodeToString(imageFile.getBytes());
                oldUser.setProfileImage(base64Image);
            } catch (IOException e) {
                throw new RuntimeException("File processing error.", e);
            }
        }

        return userRepository.save(oldUser);
    }


    @Override
    public void deleteUserByEmail(String email) {
        userRepository.deleteUserByEmail(email);
    }
}
