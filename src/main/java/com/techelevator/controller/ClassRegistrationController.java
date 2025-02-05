
package com.techelevator.controller;

import com.techelevator.dao.ClassRegistrationDao;
import com.techelevator.dao.GymClassDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.GymClass;
import com.techelevator.model.User;
import com.techelevator.model.ClassRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/registrations")
public class ClassRegistrationController {

    private final ClassRegistrationDao classRegistrationDao;
    private final GymClassDao gymClassDao;
    private final UserDao userDao;

    public ClassRegistrationController(ClassRegistrationDao classRegistrationDao, GymClassDao gymClassDao, UserDao userDao) {
        this.classRegistrationDao = classRegistrationDao;
        this.gymClassDao = gymClassDao;
        this.userDao = userDao;
    }

    // Register a user for a class (ROLE_USER or ROLE_ADMIN)
    @PostMapping("/classes/{classId}/users")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void registerForClass(@PathVariable int classId, @RequestBody ClassRegistration registration) {
        int userId = registration.getUserId();

        // Check if the class exists
        GymClass gymClass = gymClassDao.getGymClassById(classId);
        if (gymClass == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
        }

        // Check if the user exists
        User user = userDao.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Check if the user is already registered
        if (classRegistrationDao.isUserRegisteredForClass(userId, classId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already registered for this class");
        }

        // Register the user for the class
        classRegistrationDao.registerUserForClass(userId, classId);
    }

    // Unregister a user from a class (ROLE_USER or ROLE_ADMIN)
    @DeleteMapping("/classes/{classId}/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void unregisterFromClass(@PathVariable int classId, @RequestBody ClassRegistration registration) {
        int userId = registration.getUserId();

        // Check if the user is registered for the class
        if (!classRegistrationDao.isUserRegisteredForClass(userId, classId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not registered for this class");
        }

        // Unregister the user from the class
        classRegistrationDao.unregisterUserFromClass(userId, classId);
    }

    // Get all classes a user is registered for (ROLE_USER or ROLE_ADMIN)
    @GetMapping("/users/{userId}/classes")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<GymClass> getClassesForUser(@PathVariable int userId) {
        return gymClassDao.getClassesForUser(userId);
    }

    // Get all users registered for a specific class (ROLE_ADMIN)
    @GetMapping("/classes/{classId}/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsersForClass(@PathVariable int classId) {
        List<Integer> userIds = classRegistrationDao.getUserIdsByClassId(classId);
        List<User> users = new ArrayList<>();

        for (Integer userId : userIds) {
            User user = userDao.getUserById(userId);
            if (user != null) {
                users.add(user);
            }
        }

        return users;
    }
}
