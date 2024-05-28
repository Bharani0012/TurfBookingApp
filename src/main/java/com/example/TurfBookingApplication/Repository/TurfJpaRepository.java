package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Turf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurfJpaRepository extends JpaRepository<Turf, Long> {
        boolean existsByTurfName(String turfName);

        @Query("SELECT t FROM Turf t WHERE FUNCTION('acos', FUNCTION('sin', :latitude) * FUNCTION('sin', t.latitude) + " +
                "FUNCTION('cos', :latitude) * FUNCTION('cos', t.latitude) * FUNCTION('cos', :longitude - t.longitude)) * 6371 <= :radiusInKm")
        List<Turf> findTurfsByDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radiusInKm") double radiusInKm);
}

