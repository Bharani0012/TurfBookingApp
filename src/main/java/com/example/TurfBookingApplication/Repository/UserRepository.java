package com.example.TurfBookingApplication.Repository;

import com.example.TurfBookingApplication.Entity.User;

public interface UserRepository {
    User getUserById(long userId);

    User addUser(User user);

    User validateUser(String username, String password);

    User updateUser(User user, long userId);

    void deleteUser(long userId);
}
