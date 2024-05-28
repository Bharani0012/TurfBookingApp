package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.TurfImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurfImageJpaRepository extends JpaRepository<TurfImage, Long> {

}
