package com.example.TurfBookingApplication.services;

import com.example.TurfBookingApplication.Entity.User;
import com.example.TurfBookingApplication.Repository.UserJpaRepository;
import com.example.TurfBookingApplication.Repository.UserRepository;
import com.example.TurfBookingApplication.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    public User findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Override
    public User addUser(User user) {
        user.setPassword(PasswordUtil.encodePassword(user.getPassword()));

        try {
            // Check if the exact username already exists
            if (userJpaRepository.existsByUsername(user.getUsername())) {
                throw new RuntimeException("Username '" + user.getUsername() + "' is already registered.");
            }
            // Check if the exact email already exists
            if (userJpaRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Email '" + user.getEmail() + "' is already registered.");
            }
            // Check if the exact phone number already exists
            if (userJpaRepository.existsByPhoneNumber(user.getPhoneNumber())) {
                throw new RuntimeException("Phone Number '" + user.getPhoneNumber() + "' is already registered.");
            }
            return userJpaRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An unexpected error occurred while registering the user.");
        }
    }

    @Override
    public User validateUser(String username, String password) {
        // Fetch the user from the database using the username
        User user = findByUsername(username);
        // If the user exists and the password matches, return the user
        if (user != null && PasswordUtil.matchPassword(password, user.getPassword())) {
            return user;
        }
        // If the user doesn't exist or the password doesn't match, return null
        return null;
    }

    @Override
    public User updateUser(User user, long userId) {
        try {
            Optional<User> optionalOldUser = userJpaRepository.findById(userId);
            if (optionalOldUser.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            User oldUser = optionalOldUser.get();

            // Update fields
            if (user.getPhoneNumber() != null) {
                oldUser.setPhoneNumber(user.getPhoneNumber());
            }
            if (user.getEmail() != null) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getUsername() != null) {
                oldUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                oldUser.setPassword(PasswordUtil.encodePassword(user.getPassword()));
            }
            return userJpaRepository.save(oldUser);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update user information: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(long userId){

        userJpaRepository.deleteById(userId);
    }

    @Override
    public User getUserById(long userId) {
        try {
            return userJpaRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user from database: " + e.getMessage());
        }
    }
}
