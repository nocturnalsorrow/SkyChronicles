package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(String email);

    @Query("DELETE FROM User u WHERE u.email = :email")
    void deleteUserByEmail(String email);

}
