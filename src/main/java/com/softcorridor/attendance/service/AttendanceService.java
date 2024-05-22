package com.softcorridor.attendance.service;
import com.softcorridor.attendance.dto.AttendanceResponse;
import com.softcorridor.attendance.dto.WebClockInDTO;
import com.softcorridor.attendance.dto.WebClockOutDTO;
import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.model.Attendance;
import com.softcorridor.attendance.model.User;
import com.softcorridor.attendance.repository.AttendanceRepository;
import com.softcorridor.attendance.util.AttendanceReponseUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceResponse clockIn(WebClockInDTO webClockInDTO) {
        AttendanceResponse attendanceResponse = null;
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();

        try {
            Attendance attendance = new Attendance();
            attendance.setId(UUID.randomUUID().toString());
            attendance.setUserId(userId);
            attendance.setClockInTime(webClockInDTO.getClockInTime());
            boolean clockIn = attendanceRepository.clockIn(attendance);

            if (clockIn) {
                attendanceResponse = AttendanceReponseUtil.getResponse(200);
                attendanceResponse.setData(attendance);
            } else {
                attendanceResponse = AttendanceReponseUtil.getResponse(400);
                attendanceResponse.setMessage("Failed to save to DB");
            }

        } catch (Exception e) {
            e.printStackTrace();
            attendanceResponse = AttendanceReponseUtil.getResponse(500);
        }
        return attendanceResponse;

    }

    public AttendanceResponse clockOut(WebClockOutDTO webClockOutDTO) {
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();
        AttendanceResponse attendanceResponse = null;
        try {
            Attendance attendance = new Attendance();
            attendance.setId(webClockOutDTO.getAttendanceId());
            attendance.setUserId(userId);
            attendance.setClockOutTime(webClockOutDTO.getClockOutTime());
            boolean clockOut = attendanceRepository.clockOut(attendance);

            if (clockOut) {
                attendanceResponse = AttendanceReponseUtil.getResponse(200);
                attendanceResponse.setData(attendance);
            } else {
                attendanceResponse = AttendanceReponseUtil.getResponse(400);
                attendanceResponse.setMessage("Failed to save to DB");
            }

        } catch (Exception e) {
            e.printStackTrace();
            attendanceResponse = AttendanceReponseUtil.getResponse(500);
        }
        return attendanceResponse;

    }

    public AttendanceResponse getAllAttendance() {
        AttendanceResponse attendanceResponse = null;
        try {
            List<Attendance> attendanceList = attendanceRepository.getAll();
            attendanceResponse = AttendanceReponseUtil.getResponse(200);
            attendanceResponse.setData(attendanceList);
        } catch (Exception e) {
            e.printStackTrace();
            attendanceResponse = AttendanceReponseUtil.getResponse(500);
        }
        return attendanceResponse;

    }


    public AttendanceResponse deleteAttendance(String deleteId) throws AccessDeniedException {

        AttendanceResponse attendanceResponse = null;

        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();

        try {

            Attendance attendance = new Attendance();
            attendance.setId(deleteId);
            boolean deleted = attendanceRepository.delete(attendance);

            if (deleted) {
                attendanceResponse = AttendanceReponseUtil.getResponse(200);
            } else {
                attendanceResponse = AttendanceReponseUtil.getResponse(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
            attendanceResponse = AttendanceReponseUtil.getResponse(500);
        }


        return attendanceResponse;
    }



}








