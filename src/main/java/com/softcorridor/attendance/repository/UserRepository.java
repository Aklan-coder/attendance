package com.softcorridor.attendance.repository;

import com.softcorridor.attendance.enums.Role;
import com.softcorridor.attendance.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean registerUser(User user) {
        String insertUserQuery = "INSERT INTO attendance.users(firstname, lastname, " +
                "email, phone, password, enabled, role,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertUserQuery);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getPassword());
            ps.setBoolean(6, true);
            ps.setString(7, Role.USER.name());
            ps.setString(8, user.getId());

            return ps;
        });

        return returnValue > 0;
    }

    public boolean addUser(User user) {
        String insertUserQuery = "INSERT INTO attendance.users(id,firstname,lastname,middlename,email,phone, employmentType, department, designation," +
                "joinDate,password, role, created_by,created_at,modified_by,modified_at, client) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertUserQuery);
            ps.setString(1, user.getId());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getMiddlename());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getEmploymentType());
            ps.setString(8, user.getDepartment());
            ps.setString(9, user.getDesignation());
            ps.setDate(10, new java.sql.Date(user.getJoinDate().getTime()));
            ps.setString(11, user.getPassword());
            ps.setString(12, Role.USER.name());
            ps.setString(13, user.getCreatedBy());
            ps.setTimestamp(14, Timestamp.valueOf(user.getCreatedAt()));
            ps.setString(15, user.getModifiedBy());
            ps.setTimestamp(16, Timestamp.valueOf(user.getModifiedAt()));
            ps.setString(17, user.getClient_id());
            ;

            return ps;
        });
        return returnValue > 0;
    }

    public Boolean isEmailExist(String email) {
        String countQuery = "SELECT count(id) FROM attendance.users WHERE email=?";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class, email);
        return Objects.requireNonNull(count) > 0;
    }

    public Boolean isPhoneExist(String phone) {
        String countQuery = "SELECT count(id) FROM attendance.users WHERE phone=?";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class, phone);
        return Objects.requireNonNull(count) > 0;
    }

    public List<User> getAllUsers() {
        List<User> userList = jdbcTemplate.query("SELECT u.id, u.firstname, u.lastname," +
                "u.middlename, u.email, u.phone," +
                " u.enabled, u.employmentType, u.password, u.joinDate, u.role, u.department, u.designation, u.default_password, c.name AS client " +
                " FROM attendance.users AS u LEFT JOIN attendance.client AS c ON u.client" +
                " = c.id", UserRepository::mapUserRow);
        return userList;
    }

    public static User mapUserRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setMiddlename(resultSet.getString("middlename"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setClient(resultSet.getString("client"));
        user.setDefaultPassword(resultSet.getBoolean("default_password"));
        user.setEmploymentType(resultSet.getString("employmentType"));
        user.setDepartment(resultSet.getString("department"));
        user.setDesignation(resultSet.getString("designation"));
        user.setJoinDate(resultSet.getDate("joinDate"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    public boolean updateUser(User user) {
        String updateQuery = "UPDATE attendance.users SET firstname = ?, lastname = ?, middlename = ?" +
                "  WHERE id = ?";
        int update = jdbcTemplate.update(updateQuery, ps -> {
            ps.setString(1, user.getFirstname().trim());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getMiddlename());
            ps.setString(4, user.getId());
//            ps.setString(5, user.getClient_id());
        });
        return update > 0;
    }

    public boolean deleteUser(User user) {
        int update = jdbcTemplate.update("DELETE FROM attendance.users WHERE id = ? ", user.getId());
        return update > 0;
    }

    public Optional<User> findByUserId(String id) {
        String fetchUserQuery = "SELECT u.id, u.firstname, u.lastname,u.middlename, u.role, u.email, " +
                "u.phone, u.employmentType, u.department, u.designation, u.joinDate, u.password, u.enabled, " +
                "u.default_password, u.access_role, c.name AS client, c.id as client_id " +
                "FROM attendance.users AS u " +
                "LEFT JOIN attendance.client  c ON u.client = c.id " +
                "WHERE u.id = ?";

        try {
            //fetch model
            User user = jdbcTemplate.queryForObject(fetchUserQuery, (rs, rowNum) -> {
                        User userData = new User();
                        userData.setId(rs.getString("id"));
                        userData.setFirstname(rs.getString("firstname"));
                        userData.setLastname(rs.getString("lastname"));
                        userData.setMiddlename(rs.getString("middlename"));
                        userData.setEmail(rs.getString("email"));
                        userData.setRole(Role.valueOf(rs.getString("role")));
                        userData.setPhone(rs.getString("phone"));
                        userData.setPassword(rs.getString("password"));
                        userData.setClient(rs.getString("client"));
                        userData.setClient_id(rs.getString("client_id"));
                        return userData;
                    }, id
            );

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        String fetchUserQuery = "SELECT u.id, u.firstname, u.lastname, u.role, u.email, " +
                "u.phone, u.employmentType, u.department, u.designation, u.joinDate, u.password, u.enabled, " +
                "u.default_password, u.role, c.name AS client " +
                "FROM attendance.users AS u " +
                "LEFT JOIN attendance.client AS c ON u.client = c.id " +
                "WHERE u.email = ? OR u.phone = ?";
        try {
            //fetch model
            User user = jdbcTemplate.queryForObject(fetchUserQuery, (rs, rowNum) -> {
                        User userData = new User();
                        userData.setId(rs.getString("id"));
                        userData.setFirstname(rs.getString("firstname"));
                        userData.setLastname(rs.getString("lastname"));
                        userData.setRole(Role.valueOf(rs.getString("role")));
                        userData.setUsername(username);
                        userData.setPhone(rs.getString("phone"));
                        userData.setEmploymentType(rs.getString("employmentType"));
                        userData.setPassword(rs.getString("password"));
                        userData.setEnabled(rs.getBoolean("enabled"));
                        userData.setClient(rs.getString("client"));
                        userData.setDefaultPassword(rs.getBoolean("default_password"));
                        return userData;
                    },
                    username,
                    username);

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }


    public boolean updateUserEmail(User user) {
        int update = jdbcTemplate.update("UPDATE attendance.users SET email = ? WHERE id = ?", user.getEmail(), user.getId());
        return update > 0;
    }

    public boolean updateUserPhone(User user) {
        int update = jdbcTemplate.update("UPDATE attendance.users SET phone = ? WHERE id = ?", user.getPhone(), user.getId());
        return update > 0;
    }


}
