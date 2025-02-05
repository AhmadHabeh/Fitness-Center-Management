
package com.techelevator.dao;

import com.techelevator.model.Exercise;
import java.util.List;

public interface ExerciseDao {

    Exercise getExerciseById(int exerciseId);

    List<Exercise> getAllExercises();

    void createExercise(Exercise exercise);

    void updateExercise(Exercise exercise);

    void deleteExercise(int exerciseId);
}
