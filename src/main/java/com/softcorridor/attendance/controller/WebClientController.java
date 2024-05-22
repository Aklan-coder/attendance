package com.softcorridor.attendance.controller;

import com.softcorridor.attendance.dto.AttendanceResponse;
import com.softcorridor.attendance.dto.WebAddClientDTO;
import com.softcorridor.attendance.dto.WebUpdateClientDTO;
import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.exception.DuplicateEntityException;
import com.softcorridor.attendance.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/client")
@Tag(name = "Client Management")
public class WebClientController {
    private final ClientService clientService;

    public WebClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(
            description = "Client Management",
            summary = "This is used to add New Client",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi"),
                    @ApiResponse(responseCode = "400",ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403",ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500",ref = "internalServerErrorAPI")
            }
    )
    @PostMapping
    public ResponseEntity<AttendanceResponse> addClient(@Valid @RequestBody WebAddClientDTO webAddClientDTO) throws DuplicateEntityException, AccessDeniedException {
        return ResponseEntity.ok(clientService.addClient(webAddClientDTO));
    }

    @Operation(
            description = "Client Management",
            summary = "This is used to get All Clients",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi"),
                    @ApiResponse(responseCode = "400",ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "403",ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "500",ref = "internalServerErrorAPI")
            }
    )
    @GetMapping
    public ResponseEntity<AttendanceResponse> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClient());
    }

    @Operation(
            description = "Client Management",
            summary = "This is used to update an existing CLient",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi")
            }
    )
    @PutMapping
    public ResponseEntity<AttendanceResponse> updateClient(@Valid @RequestBody WebUpdateClientDTO updateClient) throws AccessDeniedException {
        return ResponseEntity.ok(clientService.updateClient(updateClient));
    }

    @Operation(
            description = "Client Management",
            summary = "Delete an existing Client",
            responses = {
                    @ApiResponse(responseCode = "200",ref = "successApi")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<AttendanceResponse> Client (@PathVariable("id") String id) throws AccessDeniedException {
        return ResponseEntity.ok(clientService.deleteClient(id));
    }
}
