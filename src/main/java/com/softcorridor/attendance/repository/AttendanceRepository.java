package com.softcorridor.attendance.repository;

import com.softcorridor.attendance.enums.Role;
import com.softcorridor.attendance.model.Attendance;
import com.softcorridor.attendance.model.Client;
import com.softcorridor.attendance.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AttendanceRepository {
    private final JdbcTemplate jdbcTemplate;

    public AttendanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean clockIn(Attendance attendance) {
        String insertQuery = "INSERT INTO attendance.attendance_record (id, user_id, clockInTime) VALUES (?, ?, ?)";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery);
            ps.setString(1, attendance.getId());
            ps.setString(2, attendance.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(attendance.getClockInTime()));
            return ps;
        });
        return returnValue > 0;
    }

    public boolean clockOut(Attendance attendance) {
        String updateAttendanceQuery = "UPDATE attendance.attendance_record SET clockOutTime = ? WHERE id = ?";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(updateAttendanceQuery);
            ps.setTimestamp(1, Timestamp.valueOf(attendance.getClockOutTime()));
            ps.setString(2, attendance.getId());
            return ps;
        });
        return returnValue > 0;
    }

    public boolean delete(Attendance attendance) {

        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM attendance.attendance_record WHERE id = ?");
            ps.setString(1, attendance.getId());
            return ps;
        });
        return returnValue > 0;
    }

    public List<Attendance> getAll() {
        List<Attendance> attendanceList = jdbcTemplate.query("SELECT * FROM attendance.attendance_record", (rs, rowNum) -> {
            Attendance attendance = new Attendance();
            attendance.setId(rs.getString("id"));
            attendance.setUserId(rs.getString("user_id"));
            attendance.setClockInTime(rs.getTimestamp("clockInTime").toLocalDateTime());
            attendance.setClockOutTime(rs.getTimestamp("clockOutTime").toLocalDateTime());
            return attendance;
        });
        return attendanceList;

    }
    public List<Attendance> findByUserId(String userId) {
        String selectQuery = "SELECT * FROM attendance.attendance_record WHERE user_id = ?";
        return jdbcTemplate.query(selectQuery, (rs, rowNum) -> {
            Attendance attendance = new Attendance();
            attendance.setId(rs.getString("id"));
            attendance.setUserId(rs.getString("user_id"));
            attendance.setClockInTime(rs.getTimestamp("clockInTime").toLocalDateTime());
            attendance.setClockOutTime(rs.getTimestamp("clockOutTime").toLocalDateTime());
            return attendance;
        }, userId);
    }


}