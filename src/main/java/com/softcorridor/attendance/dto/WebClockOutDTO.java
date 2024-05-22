package com.softcorridor.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class WebClockOutDTO {
    @Valid
    @NotBlank(message = "Attendance Id can't be empty")
    private String attendanceId;

    @Valid
    @NotBlank(message = "clock out can't be empty")
    private LocalDateTime clockOutTime;

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalDateTime getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(LocalDateTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }
}
