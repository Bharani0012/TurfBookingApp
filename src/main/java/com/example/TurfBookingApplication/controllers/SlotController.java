package com.example.TurfBookingApplication.controllers;

import com.example.TurfBookingApplication.Entity.Slot;
import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.services.JwtService;
import com.example.TurfBookingApplication.services.OwnerService;
import com.example.TurfBookingApplication.services.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("{ownerId}/turf")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OwnerService ownerService;

    private void validateOwner(String token, Long ownerId) {
        String username = jwtService.getUsernameFromToken(token);
        Owner existingOwner = ownerService.getOwnerById(ownerId);
        if (existingOwner == null || !username.equals(existingOwner.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to perform this action.");
        }
    }

    @GetMapping("/slots")
    public List<Slot> getAllSlots(@PathVariable Long ownerId, @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        return slotService.getAllSlots();
    }

    @GetMapping("/slots/{slotId}")
    public ResponseEntity<Slot> getSlotById(@PathVariable Long ownerId, @PathVariable Long slotId, @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        Slot slot = slotService.getSlotById(slotId);
        if (slot == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(slot);
    }

    @PutMapping("/slots/{slotId}")
    public ResponseEntity<Slot> updateSlot(@PathVariable Long ownerId, @PathVariable Long slotId, @RequestBody Slot slotDetails, @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        Slot updatedSlot = slotService.updateSlot(slotId, slotDetails);
        if (updatedSlot == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSlot);
    }

    @DeleteMapping("/slots/{slotId}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long ownerId, @PathVariable Long slotId, @RequestHeader(value = "Authorization") String token) {
        validateOwner(token, ownerId);
        slotService.deleteSlot(slotId);
        return ResponseEntity.noContent().build();
    }
}
