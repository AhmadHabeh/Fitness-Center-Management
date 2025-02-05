
package com.techelevator.dao;

import com.techelevator.model.EquipmentUsage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcEquipmentUsageDao implements EquipmentUsageDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcEquipmentUsageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void logEquipmentUsage(EquipmentUsage usage) {
        String sql = "INSERT INTO equipment_usage (session_id, exercise_id, date_time, reps, weight_per_rep, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING usage_id";
        Integer usageId = jdbcTemplate.queryForObject(sql, Integer.class,
                usage.getSessionId(),
                usage.getExerciseId(),
                Timestamp.valueOf(usage.getDateTime()),
                usage.getReps(),
                usage.getWeightPerRep(),
                usage.getNotes());
        usage.setUsageId(usageId);
    }

    @Override
    public EquipmentUsage getEquipmentUsageById(int usageId) {
        String sql = "SELECT * FROM equipment_usage WHERE usage_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, usageId);
        if (rs.next()) {
            return mapRowToEquipmentUsage(rs);
        }
        return null;
    }

    @Override
    public List<EquipmentUsage> getEquipmentUsageBySessionId(int sessionId) {
        String sql = "SELECT * FROM equipment_usage WHERE session_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, sessionId);
        List<EquipmentUsage> usages = new ArrayList<>();
        while (rs.next()) {
            usages.add(mapRowToEquipmentUsage(rs));
        }
        return usages;
    }

    @Override
    public List<EquipmentUsage> getEquipmentUsageByUserId(int userId) {
        String sql = "SELECT eu.* FROM equipment_usage eu " +
                "JOIN workout_session ws ON eu.session_id = ws.session_id " +
                "WHERE ws.user_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        List<EquipmentUsage> usages = new ArrayList<>();
        while (rs.next()) {
            usages.add(mapRowToEquipmentUsage(rs));
        }
        return usages;
    }

    @Override
    public List<EquipmentUsage> getAllEquipmentUsage() {
        String sql = "SELECT * FROM equipment_usage";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        List<EquipmentUsage> usages = new ArrayList<>();
        while (rs.next()) {
            usages.add(mapRowToEquipmentUsage(rs));
        }
        return usages;
    }

    @Override
    public void updateEquipmentUsage(EquipmentUsage usage) {
        String sql = "UPDATE equipment_usage SET session_id = ?, exercise_id = ?, date_time = ?, reps = ?, weight_per_rep = ?, notes = ? " +
                "WHERE usage_id = ?";
        jdbcTemplate.update(sql,
                usage.getSessionId(),
                usage.getExerciseId(),
                Timestamp.valueOf(usage.getDateTime()),
                usage.getReps(),
                usage.getWeightPerRep(),
                usage.getNotes(),
                usage.getUsageId());
    }

    @Override
    public void deleteEquipmentUsage(int usageId) {
        String sql = "DELETE FROM equipment_usage WHERE usage_id = ?";
        jdbcTemplate.update(sql, usageId);
    }

    private EquipmentUsage mapRowToEquipmentUsage(SqlRowSet rs) {
        EquipmentUsage usage = new EquipmentUsage();
        usage.setUsageId(rs.getInt("usage_id"));
        usage.setSessionId(rs.getInt("session_id"));
        usage.setExerciseId(rs.getInt("exercise_id"));
        usage.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        usage.setReps(rs.getInt("reps"));
        usage.setWeightPerRep(rs.getDouble("weight_per_rep"));
        usage.setNotes(rs.getString("notes"));
        return usage;
    }
}
