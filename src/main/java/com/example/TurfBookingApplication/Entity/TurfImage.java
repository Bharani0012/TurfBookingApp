package com.example.TurfBookingApplication.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityScan
@Table(name = "turf_image")
public class TurfImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private long imageId;

    @Column(name = "image_url", nullable = false, length = 512)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "turf_id", nullable = false)
    @JsonBackReference("turf-turfImages")
    private Turf turf;
}
