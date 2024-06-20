package com.example.TurfBookingApplication.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@EntityScan
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slot", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"start_time"}),
        @UniqueConstraint(columnNames = {"end_time"}),
})
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="slot_id",nullable = false, unique = true )
    private Long slotId;

    @Column(name = "start_time" , nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name="is_booked", nullable = false)
    private boolean isBooked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turf_id")
    @JsonBackReference("turf-slots")
    private Turf turf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonBackReference("booking-slots")
    private Booking booking;
}
