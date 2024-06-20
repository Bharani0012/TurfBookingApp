package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository {
    Booking createBooking(Long userId, Long turfId, List<Long> slotIds, LocalDateTime bookingTime);

    void cancelBooking(long bookingId);
}
