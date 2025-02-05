package com.techelevator.dao;

import com.techelevator.model.WorkoutSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcWorkoutSessionDao implements WorkoutSessionDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWorkoutSessionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void checkInUser(int userId, Integer checkedInBy) {
        String sql = "INSERT INTO workout_session (user_id, check_in_time, checked_in_by) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, Timestamp.valueOf(LocalDateTime.now()), checkedInBy);
    }

    @Override
    public void checkOutUser(int sessionId) {
        String sql = "UPDATE workout_session SET check_out_time = ? WHERE session_id = ?";
        jdbcTemplate.update(sql, Timestamp.valueOf(LocalDateTime.now()), sessionId);
    }

    @Override
    public WorkoutSession getCurrentSessionByUserId(int userId) {
        String sql = "SELECT * FROM workout_session WHERE user_id = ? AND check_out_time IS NULL ORDER BY check_in_time DESC LIMIT 1";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        if (rs.next()) {
            return mapRowToWorkoutSession(rs);
        }
        return null;
    }
    @Override
    public List<WorkoutSession> getAllWorkoutSessions() {
        String sql = "SELECT * FROM workout_session ORDER BY check_in_time DESC";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        List<WorkoutSession> sessions = new ArrayList<>();
        while (rs.next()) {
            sessions.add(mapRowToWorkoutSession(rs));
        }
        return sessions;
    }
    @Override
    public List<WorkoutSession> getWorkoutSessionsByUserId(int userId) {
        String sql = "SELECT * FROM workout_session WHERE user_id = ? ORDER BY check_in_time DESC";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        List<WorkoutSession> sessions = new ArrayList<>();
        while (rs.next()) {
            sessions.add(mapRowToWorkoutSession(rs));
        }
        return sessions;
    }

    @Override
    public WorkoutSession getSessionById(int sessionId) {
        String sql = "SELECT * FROM workout_session WHERE session_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, sessionId);
        if (rs.next()) {
            return mapRowToWorkoutSession(rs);
        }
        return null;
    }

    private WorkoutSession mapRowToWorkoutSession(SqlRowSet rs) {
        WorkoutSession session = new WorkoutSession();
        session.setSessionId(rs.getInt("session_id"));
        session.setUserId(rs.getInt("user_id"));
        session.setCheckInTime(rs.getTimestamp("check_in_time").toLocalDateTime());
        if (rs.getTimestamp("check_out_time") != null) {
            session.setCheckOutTime(rs.getTimestamp("check_out_time").toLocalDateTime());
        }
        session.setCheckedInBy((Integer) rs.getObject("checked_in_by"));
        return session;
    }
}
