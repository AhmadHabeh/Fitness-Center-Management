package com.techelevator.dao;

import com.techelevator.model.ClassRegistration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class JdbcClassRegistrationDao implements ClassRegistrationDao {
    private final JdbcTemplate jdbcTemplate;
    public JdbcClassRegistrationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void registerUserForClass(int userId, int classId) {
        String sql = "INSERT INTO class_registration (user_id, class_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, classId);
    }
    @Override
    public void unregisterUserFromClass(int userId, int classId) {
        String sql = "DELETE FROM class_registration WHERE user_id = ? AND class_id = ?";
        jdbcTemplate.update(sql, userId, classId);
    }
    @Override
    public List<ClassRegistration> getRegistrationsByUserId(int userId) {
        String sql = "SELECT * FROM class_registration WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        List<ClassRegistration> registrations = new ArrayList<>();
        while (results.next()) {
            registrations.add(mapRowToClassRegistration(results));
        }
        return registrations;

    }
    @Override
    public List<ClassRegistration> getRegistrationsByClassId(int classId) {
        String sql = "SELECT * FROM class_registration WHERE class_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, classId);
        List<ClassRegistration> registrations = new ArrayList<>();
        while (results.next()) {
            registrations.add(mapRowToClassRegistration(results));
        }
        return registrations;

    }
    @Override
    public boolean isUserRegisteredForClass(int userId, int classId) {
        String sql = "SELECT COUNT(*) FROM class_registration WHERE user_id = ? AND class_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, classId);
        return count > 0;
    }
    @Override
    public List<Integer> getUserIdsByClassId(int classId) {
        String sql = "SELECT user_id FROM class_registration WHERE class_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, classId);
        List<Integer> userIds = new ArrayList<>();
        while (results.next()) {
            userIds.add(results.getInt("user_id"));
        }
        return userIds;
    }
    @Override
    public List<Integer> getClassIdsByUserId(int userId) {
        String sql = "SELECT class_id FROM class_registration WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        List<Integer> classIds = new ArrayList<>();
        while (results.next()) {
            classIds.add(results.getInt("class_id"));
        }
        return classIds;
    }


    /* --------------Helper Method ---------*/
    private ClassRegistration mapRowToClassRegistration(SqlRowSet rs) {
        ClassRegistration registration = new ClassRegistration();
        registration.setRegistrationId(rs.getInt("registration_id"));
        registration.setUserId(rs.getInt("user_id"));
        registration.setClassId(rs.getInt("class_id"));
        registration.setRegistrationTime(rs.getTimestamp("registration_time").toLocalDateTime());
        return registration;
    }
}
