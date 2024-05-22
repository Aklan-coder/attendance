package com.softcorridor.attendance.repository;

import com.softcorridor.attendance.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

import static com.softcorridor.attendance.util.AttendanceUtil.getCurrentSQLDateTimestamp;


@Repository
@RequiredArgsConstructor
@Slf4j
public class TokenRepository {
    private final JdbcTemplate jdbcTemplate;

    public boolean saveToken(Token token) {
        String insertQuery = "INSERT INTO attendance.access_token(token,user_id) VALUES (?,?)";
        int update = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, token.getToken());
            preparedStatement.setString(2, token.getUserId());
            return preparedStatement;
        });
        return update > 0;
    }

    public boolean revokeToken(String userId) {
        String updateQuery = "UPDATE attendance.access_token SET expired = ?,revoked = ?, expiry_date = ? WHERE user_id = ?";
        int update = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setBoolean(2, true);
            preparedStatement.setTimestamp(3, getCurrentSQLDateTimestamp());
            preparedStatement.setString(4, userId);
            return preparedStatement;
        });
        return update > 0;
    }

    public Optional<Token> getToken(String jwt) {
        String selectQuery = "SELECT id,user_id,expired,revoked,logout FROM attendance.access_token WHERE token = ?";
        var token = jdbcTemplate.queryForObject(selectQuery, (rs, rowNum) -> Token.builder()
                .id(rs.getInt("id"))
                .userId(rs.getString("user_id"))
                .expired(rs.getBoolean("expired"))
                .revoked(rs.getBoolean("revoked"))
                .logout(rs.getBoolean("logout")).build(), jwt);
        return Optional.ofNullable(token);
    }

    public boolean logOut(int tokenId) {
        String updateQuery = "UPDATE attendance.access_token SET expired = ?,revoked = ?, expiry_date = ?,logout = ?,logout_date = ? WHERE id = ?";
        int update = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setBoolean(2, true);
            preparedStatement.setTimestamp(3, getCurrentSQLDateTimestamp());
            preparedStatement.setBoolean(4, true);
            preparedStatement.setTimestamp(5, getCurrentSQLDateTimestamp());
            preparedStatement.setInt(6, tokenId);
            return preparedStatement;
        });

        return update > 0;


    }


}
