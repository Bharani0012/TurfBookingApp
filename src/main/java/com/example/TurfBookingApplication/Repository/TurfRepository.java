package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.Entity.Turf;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurfRepository {
    Turf publishTurf(Turf turf);

    Owner getOwnerByTurfId(long turfId);

    Turf getTurfByTurfId(long turfId);

    void deleteTurf(Long turfId);

    Turf updateTurf(Turf turf, Long turfId);

    List<Turf> findTurfsNearby(double latitude, double longitude, double radiusInKm);
}
