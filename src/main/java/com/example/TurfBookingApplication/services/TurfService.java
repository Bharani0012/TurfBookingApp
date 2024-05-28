package com.example.TurfBookingApplication.services;

import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.Entity.Slot;
import com.example.TurfBookingApplication.Entity.Turf;
import com.example.TurfBookingApplication.Entity.TurfImage;
import com.example.TurfBookingApplication.Repository.*;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurfService implements TurfRepository, TurfImageRepository {

    @Autowired
    private TurfJpaRepository turfJpaRepository;

    @Autowired
    private TurfImageJpaRepository turfImageJpaRepository;

    @Autowired
    private SlotJpaRepository slotJpaRepository;

    @Override
    public Turf publishTurf(@NotNull Turf turf) {
       try{
           if(turfJpaRepository.existsByTurfName(turf.getTurfName())){
               throw new RuntimeException("TurfName already exists");
           }
            Turf savedTurf= turfJpaRepository.save(turf);
            initializeHourlySlots(savedTurf);
            return savedTurf;

       }catch (DataIntegrityViolationException e){
           throw new RuntimeException("An unexpected error occurred while registering the turf.");
       }
    }

    private void initializeHourlySlots(Turf turf) {
        LocalDate today = LocalDate.now();
        List<Slot> slots = new ArrayList<>();

        for (int hour = 0; hour < 24; hour++) {
            LocalDateTime startTime = LocalDateTime.of(today, LocalTime.of(hour, 0));
            LocalDateTime endTime = startTime.plusHours(1);
            Slot slot = new Slot();
            slot.setStartTime(startTime);
            slot.setEndTime(endTime);
            slot.setBooked(false);
            slot.setTurf(turf);
            slots.add(slot);
        }

        slotJpaRepository.saveAll(slots);
    }

    @Override
    public Owner getOwnerByTurfId(long turfId) {
        try{
            Turf currentTurf = turfJpaRepository.findById(turfId)
                    .orElseThrow(() -> new RuntimeException("Turf not found"));
            return currentTurf.getOwner();
        }catch (Exception e){
            throw new RuntimeException("An unexpected error occurred while getting the owner of the turf.");
        }
    }

    @Override
    public Turf getTurfByTurfId(long turfId) {
        try {
            return turfJpaRepository.findById(turfId)
                    .orElseThrow(() -> new RuntimeException("Turf not found"));
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while getting the turf.");
        }
    }

    @Override
    public void deleteTurf(Long turfId) {
        try{
            turfJpaRepository.deleteById(turfId);
        }catch (Exception e){
            throw new RuntimeException("An unexpected error occurred while deleting the turf.");
        }
    }

    @Override
    public Turf updateTurf(Turf turf, Long turfId) {
        try{
            Turf oldTurf=getTurfByTurfId(turfId);
            if(turf.getTurfName()!=null){
                oldTurf.setTurfName(turf.getTurfName());
            }
            if(turf.getDescription()!=null){
                oldTurf.setDescription(turf.getDescription());
            }
            if(turf.getLocation()!=null) {
                oldTurf.setLocation(turf.getLocation());
            }
            if(turf.getLatitude()!=null){
                oldTurf.setLatitude(turf.getLatitude());
            }
            if(turf.getLongitude()!=null){
                oldTurf.setLongitude(turf.getLongitude());
            }
            return turfJpaRepository.save(oldTurf);
        }catch (Exception e){
            throw new RuntimeException("Failed to update owner information: " + e.getMessage());
        }
    }

    @Override
    public List<Turf> findTurfsNearby(double latitude, double longitude, double radiusInKm) {
        return turfJpaRepository.findTurfsByDistance(latitude, longitude, radiusInKm);
    }

    @Override
    @Transactional
    public TurfImage addImageToTurf(Long turfId, TurfImage turfImage) {
        Turf turf = turfJpaRepository.findById(turfId)
                .orElseThrow(() -> new RuntimeException("Turf not found"));

        return turfImageJpaRepository.save(turfImage);
    }

    @Override
    public void deleteImageFromTurf(Long turfId, Long imageId) {
        try{
            turfImageJpaRepository.deleteById(imageId);
        }catch (Exception e){
            throw new RuntimeException("An unexpected error occurred while deleting the image of the turf.");
        }
    }

    @Override
    public List<TurfImage> getImageByTurfId(Long turfId) {
        Turf turf = turfJpaRepository.findById(turfId)
                .orElseThrow(() -> new RuntimeException("Turf not found"));
        return turf.getTurfImages();
    }

}
