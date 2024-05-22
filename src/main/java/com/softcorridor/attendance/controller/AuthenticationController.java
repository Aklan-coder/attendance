package com.softcorridor.attendance.controller;

import com.softcorridor.attendance.dto.AuthenticationRequest;
import com.softcorridor.attendance.dto.AuthenticationResponse;
import com.softcorridor.attendance.dto.RegisterationRequest;
import com.softcorridor.attendance.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**********************************************************
 2024 Copyright (C),  Soft Corridor Limited
 https://www.softcorridor.com
 **********************************************************
 * Author    : Soft Corridor
 * Date      : 10/05/2024
 * Time      : 16:32
 * Project   : attendance
 * Package   : com.softcorridor.attendance.controller
 **********************************************************/


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(description = "Authorization",summary = "Login to get access Token",responses = {@ApiResponse(responseCode = "200",ref = "AuthSuccessAPI"),@ApiResponse(responseCode = "403")})
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> auth(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(
                            value = "{\"username\": \"admin@gmail.com\",\"password\": \"admin\"}"
                    )
            }
    )) @Valid @RequestBody AuthenticationRequest authenticationRequest){
        log.error(authenticationRequest.getPassword());
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }



    @Hidden
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterationRequest registerationRequest) {
        log.warn("Got here for registration!!!");
        return ResponseEntity.ok(authenticationService.register(registerationRequest));
    }





    @Operation(description = "Authorization",summary = "Refresh an existing access token",responses = {@ApiResponse(responseCode = "200",ref = "AuthSuccessAPI"),@ApiResponse(responseCode = "403")})
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }



    @Hidden
    @Operation(description = "Authorization",summary = "Validate and existing access token",responses = {@ApiResponse(responseCode = "200",ref = "AuthSuccessAPI"),@ApiResponse(responseCode = "403")})
    @GetMapping("/validate/{token}")
    public void validate(@PathVariable("token") String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // log.warn(token);
        authenticationService.validateToken(token, request, response);
    }

}
