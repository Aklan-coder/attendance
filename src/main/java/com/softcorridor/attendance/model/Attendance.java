package com.softcorridor.attendance.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    private String id;

    private String userId; // Foreign key referencing User

    private LocalDateTime clockInTime;

    private LocalDateTime clockOutTime;

}
