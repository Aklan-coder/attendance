package com.softcorridor.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebDeclineLeaveDTO {
    @Valid
    @NotBlank(message = "ID can't be empty")
    private String leaveId;


}
