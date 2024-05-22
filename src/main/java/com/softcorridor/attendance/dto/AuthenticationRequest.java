package com.softcorridor.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**********************************************************
 2024 Copyright (C),  Soft Corridor Limited
 https://www.softcorridor.com                                        
 **********************************************************
 * Author    : Soft Corridor
 * Date      : 10/05/2024
 * Time      : 16:39
 * Project   : attendance
 * Package   : com.softcorridor.attendance.model
 **********************************************************/
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @Valid
    @Email(message = "Invalid email!")
    @NotBlank(message = "Username can't be empty")
    @NotNull(message = "Username can't be empty")
    private String username;
    @NotBlank(message = "Password can't be empty")
    @NotNull(message = "Password can't be empty")
    private String password;
}
