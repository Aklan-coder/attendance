package com.softcorridor.attendance.service;

import com.softcorridor.attendance.dto.AttendanceResponse;
import com.softcorridor.attendance.dto.UserDTO;
import com.softcorridor.attendance.dto.WebUpdateUserDTO;
import com.softcorridor.attendance.dto.WebAddUserDTO;
import com.softcorridor.attendance.enums.Role;
import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.exception.DuplicateEntityException;
import com.softcorridor.attendance.model.User;
import com.softcorridor.attendance.repository.UserRepository;
import com.softcorridor.attendance.util.AttendanceReponseUtil;
import com.softcorridor.attendance.util.AttendanceUtil;
import com.softcorridor.attendance.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public AttendanceResponse getAllUsers() {
        AttendanceResponse response = null;
        try {
            List<User> allUsers = userRepository.getAllUsers();
            if (allUsers != null) {
                List<UserDTO> list = allUsers.stream().map(user ->
                        {
                            UserDTO dto = new UserDTO();
                            dto.setId(user.getId());
                            dto.setFirstname(user.getFirstname());
                            dto.setLastname(user.getLastname());
                            dto.setEmail(user.getEmail());
                            dto.setPhone(user.getPhone());
                            dto.setMiddlename(user.getMiddlename());
                            dto.setEmploymentType(user.getEmploymentType());
                            dto.setDepartment(user.getDepartment());
                            dto.setDesignation(user.getDesignation());
                            dto.setJoinDate(user.getJoinDate());
                            dto.setPassword(user.getPassword());
                            return dto;
                        }
                ).toList();
                response = AttendanceReponseUtil.getResponse(200);
                response.setData(list);
            } else {
                response = AttendanceReponseUtil.getResponse(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = AttendanceReponseUtil.getResponse(500);
        }

        return response;
    }

    public AttendanceResponse addUser(WebAddUserDTO webAddUserDTO) throws DuplicateEntityException, AccessDeniedException {
        AttendanceResponse attendanceResponse = null;
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();
        Boolean emailExist = userRepository.isEmailExist(webAddUserDTO.getEmail());
        if (!emailExist) {
            Boolean phoneExist = userRepository.isPhoneExist(webAddUserDTO.getPhone());

            if (!phoneExist) {
                try {
                    log.debug("UserService:addUser Request Params {}", ValueMapper.jsonAsString(webAddUserDTO));
                    User user = new User();
                    user.setId(UUID.randomUUID().toString());
                    user.setFirstname(webAddUserDTO.getFirstname());
                    user.setLastname(webAddUserDTO.getLastname());
                    user.setMiddlename(webAddUserDTO.getMiddlename());
                    user.setEmail(webAddUserDTO.getEmail());
                    user.setPhone(webAddUserDTO.getPhone());
                    user.setEnabled(true);
                    user.setDefaultPassword(true);
                    user.setRole(Role.USER);
                    user.setPassword(passwordEncoder.encode(webAddUserDTO.getPassword()));
                    user.setEmploymentType(webAddUserDTO.getEmploymentType());
                    user.setDepartment(webAddUserDTO.getDepartment());
                    user.setDesignation(webAddUserDTO.getDesignation());
                    user.setJoinDate(webAddUserDTO.getJoinDate());
                    user.setCreatedBy(userId);
                    user.setModifiedBy(userId);
                    user.setCreatedAt(AttendanceUtil.now());
                    user.setModifiedAt(AttendanceUtil.now());
                    user.setClient_id(webAddUserDTO.getClientId());


                    boolean b = userRepository.addUser(user);
                    log.info("UserService:addUser Execution Ended");

                    if (b) {
                        attendanceResponse = AttendanceReponseUtil.getResponse(200);
                    } else {
                        attendanceResponse = AttendanceReponseUtil.getResponse(400);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    attendanceResponse = AttendanceReponseUtil.getResponse(500);
                }

            } else {
                throw new DuplicateEntityException("Phone already in-use: " + webAddUserDTO.getPhone());
            }
        } else {
            throw new DuplicateEntityException("Email already in-use: " + webAddUserDTO.getPhone());
        }
        return attendanceResponse;


    }


public AttendanceResponse updateUser(WebUpdateUserDTO webUpdateUserDto) throws AccessDeniedException {
    AttendanceResponse Response = null;
    User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userId = userPrincipal.getId();

    try {
        Optional<User> userOptional = userRepository.findByUserId(webUpdateUserDto.getId());
        User user = new User();
        if (userOptional.isPresent()) {
            user.setId(webUpdateUserDto.getId());
            user.setFirstname(webUpdateUserDto.getFirstname());
            user.setLastname(webUpdateUserDto.getLastname());
            user.setMiddlename(webUpdateUserDto.getMiddlename());
            user.setEmail(webUpdateUserDto.getEmail());
            user.setPhone(webUpdateUserDto.getPhone());
            user.setEmploymentType(webUpdateUserDto.getEmploymentType());
            user.setDepartment(webUpdateUserDto.getDepartment());
            user.setDesignation(webUpdateUserDto.getDesignation());
            user.setClient_id(webUpdateUserDto.getClientId());
            user.setModifiedBy(userId);
            user.setModifiedAt(AttendanceUtil.now());
            user.setRole(Role.USER);
        }
        if (!userRepository.isEmailExist(user.getEmail())) {
            userRepository.updateUserEmail(user);
        }

        if (!userRepository.isPhoneExist(user.getPhone())) {
            userRepository.updateUserPhone(user);
        }

        boolean b = userRepository.updateUser(user);
        if (b) {
            Response = AttendanceReponseUtil.getResponse(200);
        } else {
            Response = AttendanceReponseUtil.getResponse(400);
        }
    } catch (Exception e) {
        e.printStackTrace();
        Response = AttendanceReponseUtil.getResponse(500);
    }

    return Response;
}

public AttendanceResponse deleteUser(String deleteId) throws AccessDeniedException {

    AttendanceResponse response = null;


    User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userId = userPrincipal.getId();

    try {
        User user = new User();
        user.setId(deleteId);
        Optional<User> userOptional = userRepository.findByUserId(deleteId);

        boolean b = userRepository.deleteUser(user);
        if (b) {
            response = AttendanceReponseUtil.getResponse(200);
        } else {
            response = AttendanceReponseUtil.getResponse(400);
        }
    } catch (Exception e) {
        e.printStackTrace();
        response = AttendanceReponseUtil.getResponse(500);
    }

    return response;
}
}
