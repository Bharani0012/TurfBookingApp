package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingJpaRepository extends JpaRepository<Booking, Long> {
}
