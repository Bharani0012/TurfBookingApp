package com.example.TurfBookingApplication.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turf")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "turfId")
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
    @JsonManagedReference
    private List<TurfImage> turfImages;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true) // This annotation replaces the `Owner` object with its `ownerId`
    private Owner owner;

    @OneToMany(mappedBy = "turf", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Slot> slots;
}
