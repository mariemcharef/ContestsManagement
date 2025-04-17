package com.cp.Contests_management.auth;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.cp.Contests_management.Participant.ParticipantService;
import com.cp.Contests_management.Security.JwtService;
import com.cp.Contests_management.User.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;

//required
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ParticipantService participantService;
    private final UserRepository userRepository;


    @Transactional
    public AuthenticationResponse register(RegistrationRequest request) {
        try {
            if (userRepository.existsByName(request.getName())) {
                throw new  ResponseStatusException(HttpStatus.CONFLICT,"Name exists already,chose other one!");
            }
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new  ResponseStatusException(HttpStatus.CONFLICT,"Email exists already,chose other one!");

            }
            String name=request.getName().substring(0, 1).toUpperCase() + request.getName().substring(1);
            var user = User.builder()
                    .email(request.getEmail())
                    .name(name)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .accountLocked(false)
                    .enabled(false)
                    .role(Role.USER)
                    .build();
            repository.save(user);
            participantService.createParticipant(name, user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new  ResponseStatusException(HttpStatus.CONFLICT,"Invalid email or password");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect mail or password "));
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid email or password");
        }
    }

}