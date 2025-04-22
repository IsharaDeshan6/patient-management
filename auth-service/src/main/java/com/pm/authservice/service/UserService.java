package com.pm.authservice.service;

import com.pm.authservice.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
