package com.example.TurfBookingApplication.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
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
    @JsonBackReference
    private Turf turf;

//    @OneToOne(mappedBy = "slot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Booking booking;

}
