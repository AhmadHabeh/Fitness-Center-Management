
package com.techelevator.dao;

import com.techelevator.model.Exercise;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcExerciseDao implements ExerciseDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcExerciseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Exercise getExerciseById(int exerciseId) {
        String sql = "SELECT * FROM exercises WHERE exercise_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, exerciseId);
        if (rs.next()) {
            return mapRowToExercise(rs);
        }
        return null;
    }

    @Override
    public List<Exercise> getAllExercises() {
        String sql = "SELECT * FROM exercises";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        List<Exercise> exercises = new ArrayList<>();
        while (rs.next()) {
            exercises.add(mapRowToExercise(rs));
        }
        return exercises;
    }

    @Override
    public void createExercise(Exercise exercise) {
        String sql = "INSERT INTO exercises (name, muscle_group, description, equipment) " +
                "VALUES (?, ?, ?, ?) RETURNING exercise_id";
        Integer exerciseId = jdbcTemplate.queryForObject(sql, Integer.class,
                exercise.getName(),
                exercise.getMuscleGroup(),
                exercise.getDescription(),
                exercise.getEquipment());
        exercise.setExerciseId(exerciseId);
    }

    @Override
    public void updateExercise(Exercise exercise) {
        String sql = "UPDATE exercises SET name = ?, muscle_group = ?, description = ?, equipment = ? " +
                "WHERE exercise_id = ?";
        jdbcTemplate.update(sql,
                exercise.getName(),
                exercise.getMuscleGroup(),
                exercise.getDescription(),
                exercise.getEquipment(),
                exercise.getExerciseId());
    }

    @Override
    public void deleteExercise(int exerciseId) {
        String sql = "DELETE FROM exercises WHERE exercise_id = ?";
        jdbcTemplate.update(sql, exerciseId);
    }

    private Exercise mapRowToExercise(SqlRowSet rs) {
        Exercise exercise = new Exercise();
        exercise.setExerciseId(rs.getInt("exercise_id"));
        exercise.setName(rs.getString("name"));
        exercise.setMuscleGroup(rs.getString("muscle_group"));
        exercise.setDescription(rs.getString("description"));
        exercise.setEquipment(rs.getString("equipment"));
        return exercise;
    }
}
