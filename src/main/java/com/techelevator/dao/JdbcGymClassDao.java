package com.techelevator.dao;

import com.techelevator.model.GymClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGymClassDao implements GymClassDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGymClassDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GymClass getGymClassById(int classId) {
        String sql = "SELECT * FROM gym_class WHERE class_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, classId);
        if (rs.next()) {
            return mapRowToGymClass(rs);
        }
        return null;
    }

    @Override
    public List<GymClass> getAllGymClasses() {
        String sql = "SELECT * FROM gym_class ORDER BY start_time";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        List<GymClass> gymClasses = new ArrayList<>();
        while (rs.next()) {
            gymClasses.add(mapRowToGymClass(rs));
        }
        return gymClasses;
    }

    @Override
    public GymClass createGymClass(GymClass gymClass) {
        String sql = "INSERT INTO gym_class (name, description, start_time, end_time, capacity) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING class_id";
        Integer classId = jdbcTemplate.queryForObject(sql, Integer.class,
                gymClass.getName(),
                gymClass.getDescription(),
                gymClass.getStartTime(),
                gymClass.getEndTime(),
                gymClass.getCapacity());
        gymClass.setClassId(classId);
        return gymClass;

    }

    @Override
    public void updateGymClass(GymClass gymClass) {
        String sql = "UPDATE gym_class SET name = ?, description = ?, start_time = ?, end_time = ?, capacity = ? " +
                "WHERE class_id = ?";
        jdbcTemplate.update(sql,
                gymClass.getName(),
                gymClass.getDescription(),
                gymClass.getStartTime(),
                gymClass.getEndTime(),
                gymClass.getCapacity(),
                gymClass.getClassId());
    }

    @Override
    public void deleteGymClass(int classId) {
        String sql = "DELETE FROM gym_class WHERE class_id = ?";
        jdbcTemplate.update(sql, classId);
    }

    @Override
    public List<GymClass> getClassesForUser(int userId) {
        String sql = "SELECT gc.* FROM gym_class gc " +
                "JOIN class_registration cr ON gc.class_id = cr.class_id " +
                "WHERE cr.user_id = ? " +
                "ORDER BY gc.start_time";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        List<GymClass> gymClasses = new ArrayList<>();
        while (rs.next()) {
            gymClasses.add(mapRowToGymClass(rs));
        }
        return gymClasses;
    }

    private GymClass mapRowToGymClass(SqlRowSet rs) {
        GymClass gymClass = new GymClass();
        gymClass.setClassId(rs.getInt("class_id"));
        gymClass.setName(rs.getString("name"));
        gymClass.setDescription(rs.getString("description"));
        gymClass.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        gymClass.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
        gymClass.setCapacity(rs.getInt("capacity"));
        return gymClass;
    }
}
