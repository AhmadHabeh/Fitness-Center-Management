package com.techelevator.dao;

import com.techelevator.model.WorkoutSession;

import java.util.List;

public interface WorkoutSessionDao {

    void checkInUser(int userId, Integer checkedInBy);

    void checkOutUser(int sessionId);

    WorkoutSession getCurrentSessionByUserId(int userId);

    List<WorkoutSession> getWorkoutSessionsByUserId(int userId);

    List<WorkoutSession> getAllWorkoutSessions();


    WorkoutSession getSessionById(int sessionId);
}
