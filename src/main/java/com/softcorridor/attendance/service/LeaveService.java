package com.softcorridor.attendance.service;

import com.softcorridor.attendance.dto.AttendanceResponse;
import com.softcorridor.attendance.dto.WebAddLeaveDTO;
import com.softcorridor.attendance.dto.WebDeclineLeaveDTO;
import com.softcorridor.attendance.dto.WebUpdateLeaveDTO;
import com.softcorridor.attendance.enums.LeaveStatus;
import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.model.Leave;
import com.softcorridor.attendance.model.User;
import com.softcorridor.attendance.repository.LeaveRepository;
import com.softcorridor.attendance.util.AttendanceReponseUtil;
import com.softcorridor.attendance.util.AttendanceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public AttendanceResponse addLeave(WebAddLeaveDTO webAddLeaveDTO) {
        AttendanceResponse attendanceResponse = null;
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();
        try {
            Leave leave = new Leave();
            leave.setId(UUID.randomUUID().toString());
            leave.setStartDate(webAddLeaveDTO.getStartDate());
            leave.setEndDate(webAddLeaveDTO.getEndDate());
            leave.setReason(webAddLeaveDTO.getReason());
            leave.setStatus(LeaveStatus.PENDING);
            leave.setAppliedDate(AttendanceUtil.now());
            leave.setModifiedBy(userId);
            leave.setModifiedAt(AttendanceUtil.now());
            leave.setUserId(userId);
            leave.setCreatedAt(AttendanceUtil.now());

            boolean apply = leaveRepository.addLeave(leave);
            if (apply) {
                attendanceResponse = AttendanceReponseUtil.getResponse(200);
                attendanceResponse.setData(leave);
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

    public AttendanceResponse approveLeave(WebUpdateLeaveDTO webUpdateLeaveDTO) {
        AttendanceResponse attendanceResponse = null;
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();
        try {
            Leave leave = new Leave();
            leave.setId(webUpdateLeaveDTO.getLeaveId());
            leave.setStatus(LeaveStatus.APPROVED);
            leave.setModifiedBy(userId);
            leave.setModifiedAt(AttendanceUtil.now());
            leave.setModifiedBy(userId);
            boolean update = leaveRepository.updateLeave(leave);

            if (update) {
                attendanceResponse = AttendanceReponseUtil.getResponse(200);
                attendanceResponse.setData(leave);
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

    public AttendanceResponse declineLeave(WebDeclineLeaveDTO webDeclineLeaveDTO) {
        AttendanceResponse attendanceResponse = null;
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();
        try {
            Leave leave = new Leave();
            leave.setId(webDeclineLeaveDTO.getLeaveId());
            leave.setStatus(LeaveStatus.DECLINED);
            leave.setModifiedBy(userId);
            leave.setModifiedAt(AttendanceUtil.now());
            boolean update = leaveRepository.updateLeave(leave);

            if (update) {
                attendanceResponse = AttendanceReponseUtil.getResponse(200);
                attendanceResponse.setData(leave);
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

    public AttendanceResponse deleteLeave(String deleteId) throws AccessDeniedException {

        AttendanceResponse attendanceResponse = null;

        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();

        try {

            Leave leave = new Leave();
            leave.setId(deleteId);
            boolean deleted = leaveRepository.delete(leave);

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

    public AttendanceResponse getAllLeave() {
        AttendanceResponse attendanceResponse = null;
        try {
            List<Leave> leaveList = leaveRepository.getAllLeave();
            attendanceResponse = AttendanceReponseUtil.getResponse(200);
            attendanceResponse.setData(leaveList);
        } catch (Exception e) {
            e.printStackTrace();
            attendanceResponse = AttendanceReponseUtil.getResponse(500);
        }
        return attendanceResponse;

    }

}