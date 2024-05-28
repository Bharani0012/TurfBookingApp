package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.TurfImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurfImageRepository {
    TurfImage addImageToTurf(Long turfId, TurfImage imageUrl);

    void deleteImageFromTurf(Long turfId, Long imageId);

    List<TurfImage> getImageByTurfId(Long turfId);
}
