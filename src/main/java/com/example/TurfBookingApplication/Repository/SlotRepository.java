package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.Slot;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository {
    List<Slot> getAllSlots();

    Slot getSlotById(Long id);

    Slot updateSlot(Long id, Slot slotDetails);

    void deleteSlot(Long id);
}
