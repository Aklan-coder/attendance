package com.softcorridor.attendance.repository;

import com.softcorridor.attendance.enums.LeaveStatus;
import com.softcorridor.attendance.model.Leave;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class LeaveRepository {

    private final JdbcTemplate jdbcTemplate;

    public LeaveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean addLeave(Leave leave) {
        String query = "INSERT INTO attendance.leave (id, user_id, startDate, endDate, appliedDate, reason, status, createdAt, modifiedAt, modifiedBy) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, leave.getId());
            ps.setString(2, leave.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(leave.getStartDate()));
            ps.setTimestamp(4, Timestamp.valueOf(leave.getEndDate()));
            ps.setTimestamp(5, Timestamp.valueOf(leave.getAppliedDate()));
            ps.setString(6, leave.getReason());
            ps.setString(7, LeaveStatus.PENDING.name());
            ps.setTimestamp(8, Timestamp.valueOf(leave.getCreatedAt()));
            ps.setTimestamp(9, Timestamp.valueOf(leave.getModifiedAt()));

            ps.setString(10, (leave.getModifiedBy()));
            return ps;
        });
        return returnValue > 0;

    }

    public Optional<Leave> getLeaveById(String id) {
        Leave leave = jdbcTemplate.queryForObject("SELECT * FROM attendance.leave WHERE id = ?", LeaveRepository::mapLeaveItemRow, id);
        return Optional.ofNullable(leave);
    }


    public static Leave mapLeaveItemRow(ResultSet rs, int rowNum) throws SQLException {
        Leave leave = new Leave();
        leave.setId(rs.getString("id"));
        leave.setUserId(rs.getString("user_id"));
        leave.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
        leave.setModifiedBy(rs.getString("modified_by"));
        leave.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        leave.setReason(rs.getString("reason"));
        leave.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
        leave.setEndDate(rs.getTimestamp("endDate").toLocalDateTime());
        leave.setAppliedDate(rs.getTimestamp("appliedDate").toLocalDateTime());
        leave.setStatus(LeaveStatus.valueOf("status"));
        return leave;

    }


    public boolean updateLeave(Leave leave) {
        String query = "UPDATE attendance.leave SET status = ?, modified_by = ?, modified_at = ? WHERE id = ?";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(leave.getStatus()));
            ps.setString(2, leave.getModifiedBy());
            ps.setTimestamp(3, Timestamp.valueOf(leave.getModifiedAt()));
            ps.setString(4, leave.getId());
            return ps;
        });
        return returnValue > 0;

    }


    public boolean delete(Leave leave) {

        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM attendance.leave WHERE id = ?");
            ps.setString(1, leave.getId());
            return ps;
        });
        return returnValue > 0;
    }

    public List<Leave> getAllLeave() {
        List<Leave> leaveList = jdbcTemplate.query("SELECT * FROM attendance.leave", (rs, rowNum) -> {
            Leave leave = new Leave();
            leave.setId(rs.getString("id"));
            leave.setModifiedBy(rs.getString("modified_by") != null ? rs.getString("modified_by") : null);
            leave.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
            leave.setModifiedAt(rs.getTimestamp("modified_at") != null ? rs.getTimestamp("modified_at").toLocalDateTime() : null);
            leave.setStartDate(rs.getTimestamp("startDate") != null ? rs.getTimestamp("startDate").toLocalDateTime() : null);
            leave.setEndDate(rs.getTimestamp("endDate") != null ? rs.getTimestamp("endDate").toLocalDateTime() : null);
            leave.setAppliedDate(rs.getTimestamp("appliedDate") != null ? rs.getTimestamp("appliedDate").toLocalDateTime() : null);
            return leave;
        });
        return leaveList;
    }


}
