package com.softcorridor.attendance.controller;


import com.softcorridor.attendance.dto.AttendanceResponse;
import com.softcorridor.attendance.dto.WebUpdateUserDTO;
import com.softcorridor.attendance.dto.WebAddUserDTO;
import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.exception.DuplicateEntityException;
import com.softcorridor.attendance.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management")
public class WebUserController {
    private final UserService userService;

    public WebUserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            description = "User Management",
            summary = "This is used to add New user",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successApi"),
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )


    @PostMapping
    public ResponseEntity<AttendanceResponse> addUser(@Valid @RequestBody WebAddUserDTO webAddUserDTO) throws DuplicateEntityException, AccessDeniedException {
        return ResponseEntity.ok(userService.addUser(webAddUserDTO));
    }


    @Operation(
            description = "User Management",
            summary = "This is used to get All Users",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi"),
                    @ApiResponse(responseCode = "400",ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403",ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500",ref = "internalServerErrorAPI")
            }
    )
    @GetMapping
    public ResponseEntity<AttendanceResponse> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @Operation(
            description = "User Management",
            summary = "This is used to update an existing user",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi")
            }
    )
    @PutMapping
    public ResponseEntity<AttendanceResponse> updateUser(@Valid @RequestBody WebUpdateUserDTO webUpdateUserDTO) throws AccessDeniedException {
        return ResponseEntity.ok(userService.updateUser(webUpdateUserDTO));
    }


    @Operation(
            description = "User Management",
            summary = "Delete an existing user",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<AttendanceResponse> deleteUser(@PathVariable("id") String id) throws AccessDeniedException {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
