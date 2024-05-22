package com.softcorridor.attendance.repository;

import com.softcorridor.attendance.model.Client;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ClientRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public boolean add(Client client) {
        String insert = "INSERT INTO attendance.client(id,name,address, created_by, modified_by,modified_at) VALUES (?,?,?,?,?,?)";
        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.setString(1, client.getId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getAddress());
            ps.setString(4, client.getCreatedBy());
            ps.setString(5, client.getModifiedBy());
            ps.setTimestamp(6, Timestamp.valueOf(client.getModifiedAt()));
            return ps;
        });
        return returnValue > 0;
    }

    public boolean update(Client client) {

        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("UPDATE attendance.client SET name = ?, address = ?, modified_by = ?, modified_at = ? WHERE id = ?");
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getModifiedBy());
            ps.setTimestamp(4, Timestamp.valueOf(client.getModifiedAt()));
            ps.setString(5, client.getId());
            return ps;
        });
        return returnValue > 0;
    }

    public boolean delete(Client client) {

        int returnValue = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM attendance.client WHERE id = ?");
            ps.setString(1, client.getId());
            return ps;
        });
        return returnValue > 0;
    }

    public List<Client> getAll() {
        List<Client> clientList = jdbcTemplate.query("SELECT * FROM attendance.client", (rs, rowNum) -> {
            Client client = new Client();
            client.setId(rs.getString("id"));
            client.setName(rs.getString("name") != null ? rs.getString("name") : "");
            client.setAddress(rs.getString("address") != null ? rs.getString("address") : "");
            client.setCreatedBy(rs.getString("created_by") != null ? rs.getString("created_by") : "");
            client.setModifiedBy(rs.getString("modified_by") != null ? rs.getString("modified_by") : "");
            client.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
            client.setModifiedAt(rs.getTimestamp("modified_at") != null ? rs.getTimestamp("modified_at").toLocalDateTime() : null);
            return client;
        });
        return clientList;
    }

    public Boolean isHQExists(String name) {
        String countQuery = "SELECT count(id) FROM attendance.client WHERE name=?";
        Integer count = jdbcTemplate.queryForObject(countQuery, Integer.class, name);
        return Objects.requireNonNull(count) > 0;
    }

    public Optional<Client> getClientByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM attendance.client WHERE name = ?", (rs, rowNum) -> {
                Client client = new Client();
                client.setId(rs.getString("id"));
                client.setName(rs.getString("name") != null ? rs.getString("name") : "");
                client.setAddress(rs.getString("address") != null ? rs.getString("address") : "");
                client.setCreatedBy(rs.getString("created_by") != null ? rs.getString("created_by") : "");
                client.setModifiedBy(rs.getString("modified_by") != null ? rs.getString("modified_by") : "");
                client.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
                client.setModifiedAt(rs.getTimestamp("modified_at") != null ? rs.getTimestamp("modified_at").toLocalDateTime() : null);
                return Optional.ofNullable(client);
            }, name);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }


}

