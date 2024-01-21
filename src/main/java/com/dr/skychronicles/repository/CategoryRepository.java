package com.dr.skychronicles.repository;

import com.dr.skychronicles.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
