
package com.techelevator.dao;

import com.techelevator.model.UserDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserDetailsDao implements UserDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDetailsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails getUserDetailsByUserId(int userId) {
        String sql = "SELECT * FROM user_details WHERE user_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        if (rs.next()) {
            return mapRowToUserDetails(rs);
        }
        return null;
    }

    @Override
    public void createUserDetails(UserDetails userDetails) {
        String sql = "INSERT INTO user_details (user_id, name, email, photo, workout_goals, workout_profile) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                userDetails.getUserId(),
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getPhoto(),
                userDetails.getWorkoutGoals(),
                userDetails.getWorkoutProfile());
    }

    @Override
    public void updateUserDetails(UserDetails userDetails) {
        String sql = "UPDATE user_details SET name = ?, email = ?, photo = ?, workout_goals = ?, workout_profile = ? " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sql,
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getPhoto(),
                userDetails.getWorkoutGoals(),
                userDetails.getWorkoutProfile(),
                userDetails.getUserId());
    }

    @Override
    public void deleteUserDetails(int userId) {
        String sql = "DELETE FROM user_details WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    private UserDetails mapRowToUserDetails(SqlRowSet rs) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(rs.getInt("user_id"));
        userDetails.setName(rs.getString("name"));
        userDetails.setEmail(rs.getString("email"));
        userDetails.setPhoto(rs.getString("photo"));
        userDetails.setWorkoutGoals(rs.getString("workout_goals"));
        userDetails.setWorkoutProfile(rs.getString("workout_profile"));
        return userDetails;
    }
}
