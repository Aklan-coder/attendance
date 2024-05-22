package com.softcorridor.attendance.model;

import com.softcorridor.attendance.enums.LeaveStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
    private String id;

    private String userId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime appliedDate;

    private String reason;

    private LeaveStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String modifiedBy;

}
