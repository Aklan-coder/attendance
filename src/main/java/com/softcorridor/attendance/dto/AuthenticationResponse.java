package com.softcorridor.attendance.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**********************************************************
 2024 Copyright (C),  Soft Corridor Limited
 https://www.softcorridor.com
 **********************************************************
 * Author    : Soft Corridor
 * Date      : 10/05/2024
 * Time      : 16:40
 * Project   : attendance
 * Package   : com.softcorridor.attendance.model
 **********************************************************/
@Data
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
