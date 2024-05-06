package com.example.TurfBookingApplication.services;

import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.Repository.OwnerJpaRepository;
import com.example.TurfBookingApplication.Repository.OwnerRepository;
import com.example.TurfBookingApplication.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService implements OwnerRepository {

    @Autowired
    private OwnerJpaRepository ownerJpaRepository;

    @Override
    public Owner addOwner(Owner owner) {
        owner.setPassword(PasswordUtil.encodePassword(owner.getPassword()));
        return ownerJpaRepository.save(owner);
    }
}
