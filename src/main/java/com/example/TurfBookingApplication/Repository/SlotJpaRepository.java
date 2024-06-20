package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SlotJpaRepository extends JpaRepository<Slot, Long> {

    void deleteSlotsByTurf_TurfIdAndStartTimeBefore(long turfId, LocalDateTime of);

    boolean existsByTurf_TurfIdAndStartTimeAndEndTime(long turfId, LocalDateTime startTime, LocalDateTime endTime);
}
