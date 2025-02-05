
package com.techelevator.controller;

import com.techelevator.dao.ExerciseDao;
import com.techelevator.model.Exercise;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseDao exerciseDao;

    public ExerciseController(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    // Get all exercises (Accessible by ROLE_USER and ROLE_ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Exercise> getAllExercises() {
        return exerciseDao.getAllExercises();
    }

    // Get exercise by ID (Accessible by ROLE_USER and ROLE_ADMIN)
    @GetMapping("/{exerciseId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Exercise getExerciseById(@PathVariable int exerciseId) {
        Exercise exercise = exerciseDao.getExerciseById(exerciseId);
        if (exercise == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found");
        }
        return exercise;
    }

    // Create a new exercise (Accessible by ROLE_ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createExercise(@RequestBody Exercise exercise) {
        exerciseDao.createExercise(exercise);
    }

    // Update an exercise (Accessible by ROLE_ADMIN)
    @PutMapping("/{exerciseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateExercise(@PathVariable int exerciseId, @RequestBody Exercise exercise) {
        Exercise existingExercise = exerciseDao.getExerciseById(exerciseId);
        if (existingExercise == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found");
        }

        // Ensuring the exerciseId in path and body match
        exercise.setExerciseId(exerciseId);
        exerciseDao.updateExercise(exercise);
    }

    // Delete an exercise (Accessible by ROLE_ADMIN)
    @DeleteMapping("/{exerciseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteExercise(@PathVariable int exerciseId) {
        Exercise exercise = exerciseDao.getExerciseById(exerciseId);
        if (exercise == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found");
        }
        exerciseDao.deleteExercise(exerciseId);
    }
}
