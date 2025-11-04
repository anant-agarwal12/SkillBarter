package com.skillbarter.backend.repository;

import com.skillbarter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findByEmailAndPassword(String email, String password);
}
