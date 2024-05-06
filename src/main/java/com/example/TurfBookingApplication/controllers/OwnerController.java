package com.example.TurfBookingApplication.controllers;

import com.example.TurfBookingApplication.Entity.Owner;
import com.example.TurfBookingApplication.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/register")
    public Owner registerOwner(@RequestBody Owner owner) {

        return ownerService.addOwner(owner);
    }

}
