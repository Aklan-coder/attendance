package com.softcorridor.attendance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softcorridor.attendance.dto.AuthenticationRequest;
import com.softcorridor.attendance.dto.AuthenticationResponse;
import com.softcorridor.attendance.dto.RegisterationRequest;
import com.softcorridor.attendance.enums.Role;
import com.softcorridor.attendance.model.Token;
import com.softcorridor.attendance.model.User;
import com.softcorridor.attendance.repository.TokenRepository;
import com.softcorridor.attendance.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**********************************************************
 2024 Copyright (C),  Soft Corridor Limited
 https://www.softcorridor.com                                        
 **********************************************************
 * Author    : Soft Corridor
 * Date      : 10/05/2024
 * Time      : 16:34
 * Project   : attendance
 * Package   : com.softcorridor.attendance.service
 **********************************************************/
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;


    public AuthenticationResponse register(RegisterationRequest registerationRequest) {
        String newUserId = UUID.randomUUID().toString();
        var user = User
                .builder()
                .id(newUserId)
                .password(passwordEncoder.encode(registerationRequest.getPassword()))
                .firstname(registerationRequest.getFirstname())
                .lastname(registerationRequest.getLastname())
                .username(registerationRequest.getEmail())
                .phone(registerationRequest.getPhone())
                .role(Role.ADMIN)
                .build();

        userRepository.registerUser(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        //Insert Token
        saveToken(newUserId, jwtToken);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtToken);
        authenticationResponse.setRefreshToken(refreshToken);

        return authenticationResponse;
      //  return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        //Insert Token
        revokeAllUserTokens(user.getId());
        saveToken(user.getId(), jwtToken);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtToken);
        authenticationResponse.setRefreshToken(refreshToken);

        return authenticationResponse;
    }

    private void saveToken(String userId, String jwt) {

        var token = Token
                .builder()
                .userId(userId)
                .token(jwt)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.saveToken(token);
    }

    private void revokeAllUserTokens(String userId) {
        tokenRepository.revokeToken(userId);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }


        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            User userDetails = this.userRepository.findByUsername(username).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, userDetails)) {

                var accessToken = jwtService.generateToken(userDetails);

                revokeAllUserTokens(userDetails.getId());
                saveToken(userDetails.getId(), accessToken);

                var authResponse  = new AuthenticationResponse();
                authResponse.setAccessToken(accessToken);
                authResponse.setRefreshToken(refreshToken);



                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

        }
    }


    public void validateToken(String jwtToken, HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            jwtService.validateToken(jwtToken);
        }catch (Exception e){
            throw new RuntimeException("Invalid Token (Sign)!");
        }

        String username = jwtService.extractUsername(jwtToken);
        if (username != null) {
            User userDetails = this.userRepository.findByUsername(username).orElseThrow();
            boolean isValid = tokenRepository.getToken(jwtToken).map(token -> !token.isExpired()
                            && !token.isRevoked()
                            && !token.isLogout())
                    .orElse(false);


            if (jwtService.isTokenValid(jwtToken, userDetails) && isValid) {
                new ObjectMapper().writeValue(response.getOutputStream(), "VALID");
            }else{
                throw new RuntimeException("Invalid Token!");
            }

        }

    }
}
