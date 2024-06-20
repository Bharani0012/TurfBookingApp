package com.example.TurfBookingApplication.controllers;

import com.example.TurfBookingApplication.Entity.User;
import com.example.TurfBookingApplication.LoginAuths.LoginRequest;
import com.example.TurfBookingApplication.LoginAuths.LoginResponse;
import com.example.TurfBookingApplication.services.JwtService;
import com.example.TurfBookingApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/{userId}/get_user")
    public ResponseEntity<Object> getUser(@PathVariable Long userId, @RequestHeader(value="Authorization") String token) {
        String username = jwtService.getUsernameFromToken(token);

        // Get the user from the database
        User existingUser = userService.getUserById(userId);
        if (existingUser != null && username != null && username.equals(existingUser.getUsername())) {
            try {
                User user = userService.getUserById(userId);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to get this user.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.addUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while registering the user.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Validate the user's credentials
            User user = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());
            if (user != null) {
                // If the user is valid, generate the token
                String token = jwtService.generateToken(loginRequest.getUsername());
                return ResponseEntity.ok(new LoginResponse(token));
            } else {
                // If the user is not valid, return an error message
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while logging in the user.");
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable Long userId,
                                             @RequestHeader(value="Authorization") String token) {

        // Extract the username from the token
        String username = jwtService.getUsernameFromToken(token);

        // Get the user from the database
        User existingUser = userService.getUserById(userId);

        // Check if the username in the token matches the username of the user
        if (existingUser != null && username != null && username.equals(existingUser.getUsername())) {
            // If the usernames match, update the user
            User updatedUser = userService.updateUser(user, userId);
            return ResponseEntity.ok(updatedUser);
        } else {
            // If the usernames don't match, return an error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to update this user.");
        }
    }

    @DeleteMapping("/{userId}/delete")
    public void deleteUser(@PathVariable Long userId, @RequestHeader(value="Authorization") String token) {

        // Extract the username from the token
        String username = jwtService.getUsernameFromToken(token);

        // Get the user from the database
        User existingUser = userService.getUserById(userId);

        if (existingUser != null && username != null && username.equals(existingUser.getUsername())) {
            userService.deleteUser(userId);
        }
    }
}
