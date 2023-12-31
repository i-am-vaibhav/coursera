package com.coursera.repository;

import com.coursera.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, BigDecimal> {

    Optional<User> findByUserName(String userName);

}
