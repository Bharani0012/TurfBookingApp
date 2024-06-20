package com.example.TurfBookingApplication.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityScan
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-bookings")
    private User user;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonManagedReference("booking-slots")
    private List<Slot> slots;

    @ManyToOne(fetch = FetchType.LAZY) // Many bookings can be associated with one turf
    @JoinColumn(name = "turf_id", nullable = false) // Name of the column in the booking table
    @JsonBackReference("turf-bookings") // Prevents recursive JSON serialization
    private Turf turf;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    // Other booking-specific fields can be added here
}
