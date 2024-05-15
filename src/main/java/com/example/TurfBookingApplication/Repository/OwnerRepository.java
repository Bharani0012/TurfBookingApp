package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Owner;
import org.springframework.http.ResponseEntity;

public interface OwnerRepository {
    Owner addOwner(Owner owner);
    Owner validateOwner(String username, String password);
    Owner updateOwner(Owner owner, int ownerId);
    Owner getOwnerById(int ownerId);

    void deleteOwner(int ownerId);
}
