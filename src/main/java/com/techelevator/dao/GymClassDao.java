package com.techelevator.dao;

import com.techelevator.model.GymClass;

import java.util.List;

public interface GymClassDao {
    GymClass getGymClassById(int classId);

    List<GymClass> getAllGymClasses();

    List<GymClass> getClassesForUser(int userId);


    GymClass createGymClass(GymClass gymClass);

    void updateGymClass(GymClass gymClass);

    void deleteGymClass(int classId);

}
