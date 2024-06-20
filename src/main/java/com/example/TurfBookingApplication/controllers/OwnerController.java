package com.example.TurfBookingApplication.controllers;

import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.Entity.Turf;
import com.example.TurfBookingApplication.LoginAuths.LoginRequest;
import com.example.TurfBookingApplication.LoginAuths.LoginResponse;
import com.example.TurfBookingApplication.services.JwtService;
import com.example.TurfBookingApplication.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/{ownerId}/get_owner")
    public ResponseEntity<Object> getOwner(@PathVariable Long ownerId,
                                           @RequestHeader(value="Authorization") String token)
    {
        String username = jwtService.getUsernameFromToken(token);

        // Get the owner from the database
        Owner existingOwner = ownerService.getOwnerById(ownerId);
        if (existingOwner != null && username != null && username.equals(existingOwner.getUsername())){
            try{
                Owner owner = ownerService.getOwnerById(ownerId);
                return new ResponseEntity<>(owner, HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to get this owner.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerOwner(@RequestBody Owner owner) {
        try {
            Owner registeredOwner = ownerService.addOwner(owner);
            System.out.println(owner);
            return new ResponseEntity<>(registeredOwner, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while registering the owner.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginOwner(@RequestBody LoginRequest loginRequest) {
        try {
            // Validate the owner's credentials
            Owner owner = ownerService.validateOwner(loginRequest.getUsername(), loginRequest.getPassword());
            if (owner != null) {
                // If the owner is valid, generate the token
                String token = jwtService.generateToken(loginRequest.getUsername());
                return ResponseEntity.ok(new LoginResponse(token));
            } else {
                // If the owner is not valid, return an error message
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while logging in the owner.");
        }
    }

    @PutMapping("/{ownerId}/update")
    public ResponseEntity<Object> updateOwner(@RequestBody Owner owner, @PathVariable Long ownerId,
                                              @RequestHeader(value="Authorization") String token) {

        // Extract the username from the token
        String username = jwtService.getUsernameFromToken(token);

        // Get the owner from the database
        Owner existingOwner = ownerService.getOwnerById(ownerId);

        // Check if the username in the token matches the username of the owner
        if (existingOwner != null && username != null && username.equals(existingOwner.getUsername())) {
            // If the usernames match, update the owner
            Owner updatedOwner = ownerService.updateOwner(owner, ownerId);
            return ResponseEntity.ok(updatedOwner);
        } else {
            // If the usernames don't match, return an error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to update this owner.");
        }

    }
    @DeleteMapping("/{ownerId}/delete")
    public ResponseEntity<Object> deleteOwner(@PathVariable Long ownerId, @RequestHeader(value = "Authorization") String token) {
        // Extract the username from the token
        String username = jwtService.getUsernameFromToken(token);

        // Get the owner from the database
        Owner existingOwner = ownerService.getOwnerById(ownerId);

        if (existingOwner != null && username != null && username.equals(existingOwner.getUsername())) {
            try {
                ownerService.deleteOwner(ownerId);
                return ResponseEntity.ok("Owner deleted successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An unexpected error occurred while deleting the owner.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete this owner.");
        }
    }


    @GetMapping("/{ownerId}/turfs")
    public ResponseEntity<Object> getTurfsByOwnerId(@PathVariable Long ownerId, @RequestHeader(value="Authorization") String token) {
        String username = jwtService.getUsernameFromToken(token);
        Owner existingOwner = ownerService.getOwnerById(ownerId);
        if (existingOwner != null && username != null && username.equals(existingOwner.getUsername())) {
            try {
                List<Turf> turf=ownerService.getTurfsByOwnerId(ownerId);
                return new ResponseEntity<>(turf, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to get this turf.");
        }
    }
}
