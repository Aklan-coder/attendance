package com.softcorridor.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WebAddLeaveDTO {

    @Valid
    @NotBlank(message = "User id is required!")
    @NotNull(message = "User id is required!")
    private String userId;
    @Valid
    @NotBlank(message = "Start date is required")
    private LocalDateTime startDate;
    @Valid
    @NotBlank(message = "End date is required")
    private LocalDateTime endDate;
    @Valid
    @NotBlank(message = "Reason is required")
    private String reason;

}
