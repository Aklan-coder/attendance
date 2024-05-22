package com.softcorridor.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebUpdateClientDTO {


    @Valid
    @NotBlank(message = "ID can't be empty")
    private String id;
    @Valid
    @NotBlank(message = "Name can't be empty")
    private String name;


    @Valid
    @NotBlank(message = "Address can't be empty")
    private String address;

}
