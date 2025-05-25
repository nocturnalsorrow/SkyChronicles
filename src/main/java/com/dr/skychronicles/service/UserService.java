package com.dr.skychronicles.service;

import com.dr.skychronicles.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    boolean signUpUser(User user);

    User saveUser(User user);

    void saveUser(User user, MultipartFile imageFile, Authentication authentication);

    void deleteUserByEmail(String email);
}
