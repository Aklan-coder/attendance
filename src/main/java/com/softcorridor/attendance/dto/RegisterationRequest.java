package com.softcorridor.attendance.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**********************************************************
 2024 Copyright (C),  Soft Corridor Limited
 https://www.softcorridor.com                                        
 **********************************************************
 * Author    : Soft Corridor
 * Date      : 10/05/2024
 * Time      : 16:42
 * Project   : attendance
 * Package   : com.softcorridor.attendance.dto
 **********************************************************/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterationRequest {
    @NotBlank(message = "Firstname can't be blank")
    private String firstname;
    @NotBlank(message = "Lastname can't be blank")
    private String lastname;
    @NotBlank(message = "Email can't be blank")
    private String email;
    private String phone;
    @NotBlank(message = "Password can't be blank")
    private String password;
}
