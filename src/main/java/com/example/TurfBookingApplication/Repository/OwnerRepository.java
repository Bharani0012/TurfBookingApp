package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.Entity.Turf;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository {
    Owner addOwner(Owner owner);
    Owner validateOwner(String username, String password);
    Owner updateOwner(Owner owner, long ownerId);
    Owner getOwnerById(long ownerId);
    void deleteOwner(long ownerId);
    List<Turf> getTurfsByOwnerId(long ownerId);
}
