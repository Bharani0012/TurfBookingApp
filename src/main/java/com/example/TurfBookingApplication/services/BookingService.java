package com.example.TurfBookingApplication.services;

import com.example.TurfBookingApplication.Entity.Booking;
import com.example.TurfBookingApplication.Entity.Slot;
import com.example.TurfBookingApplication.Entity.Turf;
import com.example.TurfBookingApplication.Entity.User;
import com.example.TurfBookingApplication.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService implements BookingRepository {

    @Autowired
    BookingJpaRepository bookingJpaRepository;

    @Autowired
    TurfService turfService;

    @Autowired
    UserService userService;

    @Autowired
    SlotService slotService;

    @Override
    public Booking createBooking(Long userId, Long turfId, List<Long> slotIds, LocalDateTime bookingTime) {
        // Retrieve user, turf, and slots
        Turf turf = turfService.getTurfByTurfId(turfId);
        List<Slot> slots = slotIds.stream()
                .map(slotId -> slotService.getSlotById(slotId))
                .peek(eachSlot -> eachSlot.setBooked(true))
                .toList();
        User user = userService.getUserById(userId);

        // Create new Booking entity
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTurf(turf);
        booking.setBookingTime(bookingTime);
        List<Slot> newSlots = slots.stream()
                .peek(eachSlot -> eachSlot.setBooking(booking))
                .collect(Collectors.toList());
        booking.setSlots(newSlots);

        // Save booking
        return bookingJpaRepository.save(booking);
    }

    @Override
    public void cancelBooking(long bookingId) {
        try {
            Booking bookedSlot = bookingJpaRepository.findById(bookingId).get();
            bookedSlot.getSlots().forEach(slot -> slot.setBooked(false));
            bookingJpaRepository.delete(bookedSlot);
        } catch (Exception e) {
            throw new RuntimeException("Failed to cancel booking: " + e.getMessage());
        }
    }
}
