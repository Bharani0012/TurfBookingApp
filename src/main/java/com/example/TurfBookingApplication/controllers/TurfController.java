package com.example.TurfBookingApplication.controllers;

import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.Entity.Turf;
import com.example.TurfBookingApplication.Entity.TurfImage;
import com.example.TurfBookingApplication.services.JwtService;
import com.example.TurfBookingApplication.services.OwnerService;
import com.example.TurfBookingApplication.services.TurfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/{ownerId}/turfs")
public class TurfController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TurfService turfService;

    private void validateOwner(String token, Long ownerId) {
        String username = jwtService.getUsernameFromToken(token);
        Owner existingOwner = ownerService.getOwnerById(ownerId);
        if (existingOwner == null || !username.equals(existingOwner.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to perform this action.");
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<Object> publishTurf(@PathVariable Long ownerId, @RequestHeader(value = "Authorization") String token, @Valid @RequestBody Turf turf) {
        validateOwner(token, ownerId);
        if (!Objects.equals(turf.getOwner().getOwnerId(), ownerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to publish this turf.");
        }

        try {
            Turf addedTurf = turfService.publishTurf(turf);
            return ResponseEntity.ok(addedTurf);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{turfId}")
    public ResponseEntity<Object> getTurf(@PathVariable Long ownerId, @PathVariable Long turfId, @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        try {
            Turf turf = turfService.getTurfByTurfId(turfId);
            if (!Objects.equals(turf.getOwner().getOwnerId(), ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this turf.");
            }
            return ResponseEntity.ok(turf);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turf not found");
        }
    }

    @GetMapping("/{turfId}/owner")
    public ResponseEntity<Object> getOwner(@PathVariable Long ownerId, @PathVariable Long turfId, @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        try {
            Owner owner = turfService.getOwnerByTurfId(turfId);
            if (!Objects.equals(owner.getOwnerId(), ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this owner.");
            }
            return ResponseEntity.ok(owner);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");
        }
    }

    @PutMapping("/{turfId}")
    public ResponseEntity<Object> updateTurf(@PathVariable Long ownerId, @PathVariable Long turfId, @RequestHeader(value = "Authorization") String token, @Valid @RequestBody Turf turf) {
        validateOwner(token, ownerId);
        try {
            Turf existingTurf = turfService.getTurfByTurfId(turfId);
            if (!Objects.equals(existingTurf.getOwner().getOwnerId(), ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this turf.");
            }
            Turf updatedTurf = turfService.updateTurf(turf, turfId);
            return ResponseEntity.ok(updatedTurf);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turf not found");
        }
    }

    @DeleteMapping("/{turfId}")
    public ResponseEntity<Object> deleteTurf(@PathVariable Long ownerId, @PathVariable Long turfId, @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        try {
            Turf turf = turfService.getTurfByTurfId(turfId);
            if (!Objects.equals(turf.getOwner().getOwnerId(), ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this turf.");
            }
            turfService.deleteTurf(turfId);
            return ResponseEntity.ok(turf.getTurfName() + " has been deleted.");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turf not found");
        }
    }

    @PostMapping("/{turfId}/images")
    public ResponseEntity<Object> addImage(@PathVariable Long ownerId, @PathVariable Long turfId, @RequestHeader(value = "Authorization") String token, @Valid @RequestBody TurfImage turfImage) {
        validateOwner(token, ownerId);
        try {
            Turf turf = turfService.getTurfByTurfId(turfId);
            if (!Objects.equals(turf.getOwner().getOwnerId(), ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to add images to this turf.");
            }
            TurfImage addedImage = turfService.addImageToTurf(turfId, turfImage);
            return ResponseEntity.ok(addedImage);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turf not found");
        }
    }

    @GetMapping("/{turfId}/images")
    public ResponseEntity<Object> getImages(@PathVariable Long ownerId, @PathVariable Long turfId, @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        try {
            Turf turf = turfService.getTurfByTurfId(turfId);
            if (!Objects.equals(turf.getOwner().getOwnerId(), ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view images of this turf.");
            }
            List<TurfImage> images = turfService.getImageByTurfId(turfId);
            return ResponseEntity.ok(images);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turf not found");
        }
    }

    @DeleteMapping("/{turfId}/images/{imageId}")
    public ResponseEntity<Object> deleteImage(@PathVariable Long ownerId, @PathVariable Long turfId, @PathVariable Long imageId,
                                              @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        try {
            Turf turf = turfService.getTurfByTurfId(turfId);
            if (!Objects.equals(turf.getOwner().getOwnerId(), ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete images of this turf.");
            }
            turfService.deleteImageFromTurf(turfId, imageId);
            return ResponseEntity.ok("Image has been deleted.");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turf or image not found");
        }
    }
    @GetMapping("/turfs/nearby")
    public ResponseEntity<List<Turf>> findTurfsNearby(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("radiusInKm") double radiusInKm,
            @PathVariable Long ownerId, @RequestHeader(value = "Authorization") String token)
    {

        validateOwner(token, ownerId);

        List<Turf> nearbyTurfs = turfService.findTurfsNearby(latitude, longitude, radiusInKm);

        if (nearbyTurfs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(nearbyTurfs, HttpStatus.OK);
        }
    }
}
