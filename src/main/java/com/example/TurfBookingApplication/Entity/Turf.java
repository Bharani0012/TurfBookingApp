package com.example.TurfBookingApplication.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityScan
@Table(name = "turf")
public class Turf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "turf_id")
    private Long turfId;

    @Column(name = "turf_name", nullable = false)
    private String turfName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "turf", cascade = CascadeType.ALL)
    @JsonManagedReference("turf-turfImages")
    private List<TurfImage> turfImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonBackReference("owner-turfs")
    private Owner owner;

    @OneToMany(mappedBy = "turf", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("turf-slots")
    private Set<Slot> slots;

    @OneToMany(mappedBy = "turf", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("turf-bookings")
    private List<Booking> bookings;
}
