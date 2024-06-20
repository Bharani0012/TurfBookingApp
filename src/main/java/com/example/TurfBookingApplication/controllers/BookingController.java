package com.example.TurfBookingApplication.controllers;

import com.example.TurfBookingApplication.Entity.Booking;
import com.example.TurfBookingApplication.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create/{userId}/{turfId}")
    public ResponseEntity<Object> createBooking(@PathVariable Long userId, @PathVariable Long turfId,
                                                @RequestParam("slotIds") List<Long> slotIds,
                                                @RequestParam("bookingTime") LocalDateTime bookingTime) {
        try {
            Booking booking = bookingService.createBooking(userId, turfId, slotIds, bookingTime);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while creating the booking.");
        }
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<Object> cancelBooking(@PathVariable Long bookingId){
        try{
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking cancelled successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Cancellation Failed");
        }
    }
}
