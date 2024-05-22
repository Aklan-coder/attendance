package com.softcorridor.attendance.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;

    private String firstname;

    private String middlename;

    private String lastname;

    private String phone;

    private String email;

    private String employmentType;

    private String department;

    private String role;

    private String designation;

    private Date joinDate;

    private String password;

}
