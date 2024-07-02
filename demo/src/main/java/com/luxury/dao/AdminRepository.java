package com.luxury.dao;

import com.luxury.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sarah
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);

}
