package com.example.TurfBookingApplication.services;

import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.Entity.Turf;
import com.example.TurfBookingApplication.Repository.OwnerJpaRepository;
import com.example.TurfBookingApplication.Repository.OwnerRepository;
import com.example.TurfBookingApplication.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService implements OwnerRepository {

    @Autowired
    private OwnerJpaRepository ownerJpaRepository;

    public Owner findByOwnerName(String ownerName) {
        return ownerJpaRepository.findByUsername(ownerName);
    }

    @Override
    public Owner addOwner(Owner owner) {
        owner.setPassword(PasswordUtil.encodePassword(owner.getPassword()));

        try {
            // Check if the exact username already exists
            if (ownerJpaRepository.existsByUsername(owner.getUsername())) {
                throw new RuntimeException("Username '" + owner.getUsername() + "' is already registered.");
            }
            // Check if the exact Email already exists
            if (ownerJpaRepository.existsByEmail(owner.getEmail())) {
                throw new RuntimeException("Email '" + owner.getEmail() + "' is already registered.");
            }
            // Check if the exact phone number already exists
            if (ownerJpaRepository.existsByPhoneNumber(owner.getPhoneNumber())) {
                throw new RuntimeException("Phone Number '" + owner.getPhoneNumber() + "' is already registered.");
            }
            return ownerJpaRepository.save(owner);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An unexpected error occurred while registering the owner.");
        }
    }

    @Override
    public Owner validateOwner(String username, String password) {
        // Fetch the owner from the database using the username
        Owner owner = findByOwnerName(username);
        // If the owner exists and the password matches, return the owner
        if (owner != null && PasswordUtil.matchPassword(password, owner.getPassword())) {
            return owner;
        }
        // If the owner doesn't exist or the password doesn't match, return null
        return null;
    }

    @Override
    public Owner updateOwner(Owner owner, long ownerId) {

        try{
            Owner oldOwner = ownerJpaRepository.findById(ownerId).get();

            //updating Phone number
            if(owner.getPhoneNumber() != null) {
                oldOwner.setPhoneNumber(owner.getPhoneNumber());
            }
            // updating Email
            if(owner.getEmail() != null) {
                oldOwner.setEmail(owner.getEmail());
            }
            // updating username
            if(owner.getUsername() != null) {
                oldOwner.setUsername(owner.getUsername());
            }
            //updating password
            if(owner.getPassword() != null) {
                oldOwner.setPassword(PasswordUtil.encodePassword(owner.getPassword()));
            }
            //updating First Name
            if(owner.getFirstName()!=null){
                oldOwner.setFirstName(owner.getFirstName());
            }
            //updating Last Name
            if(owner.getLastName()!=null){
                oldOwner.setLastName(owner.getLastName());
            }
            return ownerJpaRepository.save(oldOwner);

        }catch (Exception e) {
            throw new RuntimeException("Failed to update owner information: " + e.getMessage());
        }
    }

    @Override
    public void deleteOwner(long ownerId) {
        ownerJpaRepository.deleteById(ownerId);
    }

    @Override
    public List<Turf> getTurfsByOwnerId(long ownerId) {
        Owner owner=ownerJpaRepository.findById(ownerId).get();
        if(owner.getUsername()==null){
            throw new RuntimeException("Owner not found");
        }

        try{
            return owner.getTurfs();
        }catch (Exception e){
            throw new RuntimeException("Failed to retrieve turfs from database: " + e.getMessage());
        }

    }

    @Override
    public Owner getOwnerById(long ownerId) {
        try{
            return ownerJpaRepository.findById(ownerId).get();
        }catch (Exception e){
            throw new RuntimeException("Failed to retrieve owner from database: " + e.getMessage());
        }

    }

}
