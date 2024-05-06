package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerJpaRepository extends JpaRepository<Owner, Integer> {
}
