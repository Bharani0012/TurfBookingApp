package com.example.TurfBookingApplication.services;

import com.example.TurfBookingApplication.Entity.Slot;
import com.example.TurfBookingApplication.Repository.SlotJpaRepository;
import com.example.TurfBookingApplication.Repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class SlotService implements SlotRepository {

    @Autowired
    private SlotJpaRepository slotJpaRepository;

    @Override
    public List<Slot> getAllSlots() {
        try {
            return slotJpaRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch all slots", e);
        }
    }

    @Override
    public Slot getSlotById(Long id) {
        try {
            Optional<Slot> slot = slotJpaRepository.findById(id);
            if (slot.isPresent()) {
                return slot.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot with ID " + id + " not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch slot by ID", e);
        }
    }

    @Override
    public Slot updateSlot(Long id, Slot slotDetails) {
        try {
            Optional<Slot> optionalSlot = slotJpaRepository.findById(id);
            if (optionalSlot.isPresent()) {
                Slot slot = optionalSlot.get();
                if (slot.isBooked()) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update a booked slot");
                }
                slot.setStartTime(slotDetails.getStartTime());
                slot.setEndTime(slotDetails.getEndTime());
                slot.setBooked(slotDetails.isBooked());
                slot.setTurf(slotDetails.getTurf());
                return slotJpaRepository.save(slot);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot with ID " + id + " not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update slot", e);
        }
    }

    @Override
    public void deleteSlot(Long id) {
        try {
            Optional<Slot> optionalSlot = slotJpaRepository.findById(id);
            if (optionalSlot.isPresent()) {
                slotJpaRepository.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot with ID " + id + " not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete slot", e);
        }
    }
}
