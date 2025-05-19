package com.pm.authservice.service;

import com.pm.authservice.dto.request.LoginRequestDTO;

import java.util.Optional;

public interface AuthService {

    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);
    boolean validateToken(String substring);

}
