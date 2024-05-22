package com.softcorridor.attendance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private String id;
    private String name;
    private String address;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

}
