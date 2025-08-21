package com.techtez.service;

import com.techtez.entity.User;
import com.techtez.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save or update the user
    public User createOrUpdateUser(User user) {
        // Check if the email already exists in the database
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);  // Proceed with saving the user if no duplicate email found.
    }

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchElementException("User not found in the database");
        }
    }

    // Delete a user by ID
    public void deleteUser(Integer id) {
        // Check if the user exists before deleting
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("User not found in the database"); 
        }
    }
 // Delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();  // This will delete all users in the database
    }

}
