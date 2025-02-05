
package com.techelevator.controller;

import com.techelevator.dao.GymClassDao;
import com.techelevator.model.GymClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class GymClassController {

    private final GymClassDao gymClassDao;

    @Autowired
    public GymClassController(GymClassDao gymClassDao) {
        this.gymClassDao = gymClassDao;
    }

    // Get all classes (View Schedule)
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<GymClass> getAllClasses() {
        return gymClassDao.getAllGymClasses();
    }

    // Get a specific class by ID
    @GetMapping("/{classId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public GymClass getClassById(@PathVariable int classId) {
        GymClass gymClass = gymClassDao.getGymClassById(classId);
        if (gymClass == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
        }
        return gymClass;
    }

    // (Optional) Add a new class (Admin or Employee)
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public GymClass addGymClass(@RequestBody GymClass gymClass) {
        return gymClassDao.createGymClass(gymClass);
    }

    // (Optional) Update an existing class (Admin or Employee)
    // Update an existing class (Admin or Employee)
    @PutMapping("/{classId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void updateGymClass(@PathVariable int classId, @RequestBody GymClass gymClass) {
        gymClass.setClassId(classId);
        gymClassDao.updateGymClass(gymClass);
    }

    // (Optional) Delete a class (Admin only)
    // Delete a class (Admin only)
    @DeleteMapping("/{classId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGymClass(@PathVariable int classId) {
        gymClassDao.deleteGymClass(classId);
    }
}

