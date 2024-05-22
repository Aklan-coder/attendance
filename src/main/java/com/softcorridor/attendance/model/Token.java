package com.softcorridor.attendance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    private int id;
    private String userId;
    private String token;
    private boolean expired;
    private boolean revoked;
    private boolean logout;

}
