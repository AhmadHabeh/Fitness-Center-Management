
package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.WorkoutSessionDao;
import com.techelevator.model.User;
import com.techelevator.model.WorkoutSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class WorkoutSessionController {

    private final WorkoutSessionDao workoutSessionDao;
    private final UserDao userDao;

    public WorkoutSessionController(WorkoutSessionDao workoutSessionDao, UserDao userDao) {
        this.workoutSessionDao = workoutSessionDao;
        this.userDao = userDao;
    }

    // Check-in a user (ROLE_USER or ROLE_ADMIN)
    @PostMapping("/check-in")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void checkInUser(@RequestBody WorkoutSession session) {
        int userId = session.getUserId();
        Integer checkedInBy = session.getCheckedInBy(); // Nullable

        // Check if the user exists
        User user = userDao.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Check if the user is already checked in
        WorkoutSession existingSession = workoutSessionDao.getCurrentSessionByUserId(userId);
        if (existingSession != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already checked in");
        }

        // Check-in the user
        workoutSessionDao.checkInUser(userId, checkedInBy);
    }

    // Check-out a user (ROLE_USER or ROLE_ADMIN)
    @PostMapping("/check-out")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void checkOutUser(@RequestBody WorkoutSession session) {
        int userId = session.getUserId();

        // Get the current session
        WorkoutSession existingSession = workoutSessionDao.getCurrentSessionByUserId(userId);
        if (existingSession == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not currently checked in");
        }

        // Check-out the user
        workoutSessionDao.checkOutUser(existingSession.getSessionId());
    }

    // Get current session for a user (ROLE_USER or ROLE_ADMIN)
    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public WorkoutSession getCurrentSession(@RequestBody WorkoutSession session) {
        int userId = session.getUserId();
        WorkoutSession existingSession = workoutSessionDao.getCurrentSessionByUserId(userId);
        if (existingSession == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No active session found for user");
        }
        return existingSession;
    }

    // Get all workout sessions for a user (ROLE_USER or ROLE_ADMIN)
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<WorkoutSession> getWorkoutSessionsByUserId(@PathVariable int userId) {
        return workoutSessionDao.getWorkoutSessionsByUserId(userId);
    }

    // Get all workout sessions (Admin only)
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<WorkoutSession> getAllWorkoutSessions() {
        return workoutSessionDao.getAllWorkoutSessions();
    }
}
