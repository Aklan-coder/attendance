package com.softcorridor.attendance.controller;


import com.softcorridor.attendance.dto.AttendanceResponse;
import com.softcorridor.attendance.dto.WebClockInDTO;
import com.softcorridor.attendance.dto.WebClockOutDTO;
import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.exception.DuplicateEntityException;
import com.softcorridor.attendance.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/attendance")
@Tag(name = "Attendance Management")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }


    @Operation(
            description = "Attendance Management",
            summary = "This is used to Clock in User",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successApi"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )


    @PostMapping
    public ResponseEntity<AttendanceResponse> clockIn(@Valid @RequestBody WebClockInDTO webClockInDTO) throws DuplicateEntityException, AccessDeniedException {
        return ResponseEntity.ok(attendanceService.clockIn(webClockInDTO));
    }



    @Operation(
            description = "Attendance Management",
            summary = "This is used to clock Out user",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successApi"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )


    @PutMapping
    public ResponseEntity<AttendanceResponse> clockOut(@Valid @RequestBody WebClockOutDTO webClockOutDTO) throws DuplicateEntityException, AccessDeniedException {
        return ResponseEntity.ok(attendanceService.clockOut(webClockOutDTO));
    }


    @Operation(
            description = "Attendance",
            summary = "This is used to get All Attendance",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi"),
                    @ApiResponse(responseCode = "400",ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403",ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500",ref = "internalServerErrorAPI")
            }
    )
    @GetMapping
    public ResponseEntity<AttendanceResponse> getAttendance(){
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }


    @Operation(
            description = "Attendance Management",
            summary = "Delete an existing existing",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<AttendanceResponse> deleteAttendance(@PathVariable("id") String id) throws AccessDeniedException {
        return ResponseEntity.ok(attendanceService.deleteAttendance(id));
    }




}
