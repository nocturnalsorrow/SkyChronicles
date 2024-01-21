package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {

}
