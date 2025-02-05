
package com.techelevator.controller;

import com.techelevator.dao.EquipmentUsageDao;
import com.techelevator.dao.ExerciseDao;
import com.techelevator.dao.WorkoutSessionDao;
import com.techelevator.model.EquipmentUsage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/equipment-usage")
public class EquipmentUsageController {

    private final EquipmentUsageDao equipmentUsageDao;
    private final WorkoutSessionDao workoutSessionDao;
    private final ExerciseDao exerciseDao;

    public EquipmentUsageController(EquipmentUsageDao equipmentUsageDao, WorkoutSessionDao workoutSessionDao, ExerciseDao exerciseDao) {
        this.equipmentUsageDao = equipmentUsageDao;
        this.workoutSessionDao = workoutSessionDao;
        this.exerciseDao = exerciseDao;
    }

    // Logging equipment usage (ROLE_USER)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void logEquipmentUsage(@RequestBody EquipmentUsage usage) {
        // Validation
        if (workoutSessionDao.getCurrentSessionByUserId(usage.getSessionId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session is not active or does not exist");
        }

        // Validation
        if (exerciseDao.getExerciseById(usage.getExerciseId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exercise does not exist");
        }

        // Setting the date and time if not provided
        if (usage.getDateTime() == null) {
            usage.setDateTime(LocalDateTime.now());
        }

        equipmentUsageDao.logEquipmentUsage(usage);
    }

    // Getting equipment usage by session ID (ROLE_USER or ROLE_ADMIN)
    @GetMapping("/session/{sessionId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<EquipmentUsage> getEquipmentUsageBySessionId(@PathVariable int sessionId) {
        return equipmentUsageDao.getEquipmentUsageBySessionId(sessionId);
    }

    // Getting equipment usage by user ID (ROLE_ADMIN)
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<EquipmentUsage> getEquipmentUsageByUserId(@PathVariable int userId) {
        return equipmentUsageDao.getEquipmentUsageByUserId(userId);
    }

    // Getting all equipment usage (ROLE_ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<EquipmentUsage> getAllEquipmentUsage() {
        return equipmentUsageDao.getAllEquipmentUsage();
    }

    // Updating equipment usage (ROLE_ADMIN)
    @PutMapping("/{usageId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateEquipmentUsage(@PathVariable int usageId, @RequestBody EquipmentUsage usage) {
        EquipmentUsage existingUsage = equipmentUsageDao.getEquipmentUsageById(usageId);
        if (existingUsage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment usage not found");
        }

        // Ensuring the usageId in path and body match
        usage.setUsageId(usageId);                     //sets the unique identifier for the usage object.
        equipmentUsageDao.updateEquipmentUsage(usage); //updates the database record associated with that usageId using the data from the usage object.
    }

    // Delete equipment usage (ROLE_ADMIN)
    @DeleteMapping("/{usageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteEquipmentUsage(@PathVariable int usageId) {
        EquipmentUsage usage = equipmentUsageDao.getEquipmentUsageById(usageId);
        if (usage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment usage not found");
        }
        equipmentUsageDao.deleteEquipmentUsage(usageId);
    }
}
