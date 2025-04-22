package com.pm.authservice.controller;


import com.pm.authservice.dto.request.LoginRequestDTO;
import com.pm.authservice.dto.response.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import com.pm.authservice.util.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<StandardResponse> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if (tokenOptional.isEmpty()) {
            return new ResponseEntity<>(
                    new StandardResponse(401, "Invalid username or password", null),
                    HttpStatus.UNAUTHORIZED
            );
        }

        String token = tokenOptional.get();
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token);
        return new ResponseEntity<>(
                new StandardResponse(200, "Login successful", loginResponseDTO),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Validate Token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader){
        // Authorization: Bearer <token>

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
