package com.example.TurfBookingApplication.LoginAuths;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String jwt_token;
}
