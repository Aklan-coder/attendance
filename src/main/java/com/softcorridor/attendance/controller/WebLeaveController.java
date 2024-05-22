package com.softcorridor.attendance.controller;


import com.softcorridor.attendance.dto.*;
import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.exception.DuplicateEntityException;
import com.softcorridor.attendance.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leave")
@Tag(name = "Leave Management")

public class WebLeaveController {
    private final LeaveService leaveService;

    public WebLeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @Operation(
            description = "Leave Management",
            summary = "This is used to Add/Apply for Leave",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successApi"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )

    @PostMapping
    public ResponseEntity<AttendanceResponse> ApplyForLeave(@Valid @RequestBody WebAddLeaveDTO webAddLeaveDTO) throws DuplicateEntityException, AccessDeniedException {
        return ResponseEntity.ok(leaveService.addLeave(webAddLeaveDTO));
    }

    @Operation(
            description = "Leave Management",
            summary = "This is used to Approve  Leave",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successApi"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )

    @PutMapping("/approve/")
    public ResponseEntity<AttendanceResponse> ApproveLeave(@Valid @RequestBody WebUpdateLeaveDTO webUpdateLeaveDTO) throws DuplicateEntityException, AccessDeniedException {
        return ResponseEntity.ok(leaveService.approveLeave(webUpdateLeaveDTO));
    }

    @Operation(
            description = "Leave Management",
            summary = "This is used to Decline Leave",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successApi"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )

    @PutMapping("/decline/")
    public ResponseEntity<AttendanceResponse> DeclineLeave(@Valid @RequestBody WebDeclineLeaveDTO webDeclineLeaveDTO) throws DuplicateEntityException, AccessDeniedException {
        return ResponseEntity.ok(leaveService.declineLeave(webDeclineLeaveDTO));
    }

    @Operation(
            description = "Leave Management",
            summary = "This is used to get All Leave",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successApi"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )

    @GetMapping
    public ResponseEntity<AttendanceResponse> getAllLeave() {
        return ResponseEntity.ok(leaveService.getAllLeave());
    }

    @Operation(
            description = "Leave Management",
            summary = "This is used to Delete Leave",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successApi"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<AttendanceResponse> deleteLeave(@PathVariable("id") String id) throws AccessDeniedException {
        return ResponseEntity.ok(leaveService.deleteLeave(id));
    }


}

