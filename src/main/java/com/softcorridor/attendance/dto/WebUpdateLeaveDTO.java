package com.softcorridor.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebUpdateLeaveDTO {
    @Valid
    @NotBlank(message = "Leave id is required!")
    @NotNull(message = "Leave id is required!")
    private String leaveId;

}
