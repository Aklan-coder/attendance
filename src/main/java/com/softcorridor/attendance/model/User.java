package com.softcorridor.attendance.model;

import com.softcorridor.attendance.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    private String id;

    private String firstname;

    private String lastname;

    private String middlename;

    private String username;

    private Role role;

    private String email;

    private String phone;

    private String employmentType;

    private String department;

    private String designation;

    private Date joinDate;

    private String password;

    private boolean enabled;

    private boolean defaultPassword;

    private String createdBy;

    private LocalDateTime createdAt;

    private String modifiedBy;

    private LocalDateTime modifiedAt;
    private String client;

    private String client_id;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
