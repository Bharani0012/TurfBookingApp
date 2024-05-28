package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotJpaRepository extends JpaRepository<Slot, Long> {
}
