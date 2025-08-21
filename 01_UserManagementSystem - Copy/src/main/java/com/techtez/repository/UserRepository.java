package com.techtez.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techtez.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
	// Spring Data JPA automatically generates the method based on naming convention
    boolean existsByEmail(String email);  // No need for @Query, just use this
    Optional<User> findById(Integer id); // Find user by ID

}
