package com.example.subscribly.controller;

import com.example.subscribly.dto.AuthResponse;
import com.example.subscribly.dto.LoginRequest;
import com.example.subscribly.model.User;
import com.example.subscribly.repository.UserRepository;
import com.example.subscribly.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );

            String email = request.getEmail();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

            String token = jwtService.generateToken(user);
            Long tenantId = user.getTenant() != null ? user.getTenant().getId() : null;

            return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), tenantId));
        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
