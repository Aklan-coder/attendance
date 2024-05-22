package com.softcorridor.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebUpdateUserDTO {

    @Valid
    @NotBlank(message = "Id is required")
    private String id;


    @Valid
    @NotBlank(message = "First Name is required!")
    @NotNull(message = "First Name is required!")
    private String firstname;

    private String middlename;

    @NotBlank(message = "Last Name is required!")
    @NotNull(message = "Last Name is required!")
    private String lastname;


    @Email(message = "Invalid email!")
    @NotBlank(message = "Email is required!")
    @NotNull(message = "Email is required!")
    private String email;

    @NotBlank(message = "Phone is required!")
    @NotNull(message = "Phone is required!")
    @Pattern(regexp = "^\\d{11}$",message = "Invalid mobile number entered")
    private String phone;

    @Valid
    @NotNull(message = "Employment type is required!")
    @NotBlank(message = "Employment type is required")
    private String employmentType;

    @Valid
    @NotNull (message = "Department is required")
    @NotBlank(message = "Department is required")
    private String department;

    @Valid
    @NotNull(message = "Designation is required")
    @NotBlank(message = "Designation is required")
    private String designation;

    @Valid
    @NotBlank(message = "Role is required")
    private String role;

    @Valid
    @NotNull(message = "Client Id   is required")
    @NotBlank(message = "Access Role is required")
    private String clientId;


}
