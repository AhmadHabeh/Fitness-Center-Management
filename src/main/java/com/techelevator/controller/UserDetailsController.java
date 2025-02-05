
package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.UserDetailsDao;
import com.techelevator.model.User;
import com.techelevator.model.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user-details")
public class UserDetailsController {

    private final UserDetailsDao userDetailsDao;
    private final UserDao userDao;

    public UserDetailsController(UserDetailsDao userDetailsDao, UserDao userDao) {
        this.userDetailsDao = userDetailsDao;
        this.userDao = userDao;
    }

    // Get user details by user ID (Accessible by ROLE_USER and ROLE_ADMIN)
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public UserDetails getUserDetailsById(@PathVariable int userId) {
        User user = userDao.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserDetails userDetails = userDetailsDao.getUserDetailsByUserId(userId);
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User details not found");
        }
        return userDetails;
    }

    // Create user details (Accessible by ROLE_ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createUserDetails(@RequestBody UserDetails userDetails) {
        // checking if the user exists
        User user = userDao.getUserById(userDetails.getUserId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        // Checking if user details already exist
        if (userDetailsDao.getUserDetailsByUserId(userDetails.getUserId()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User details already exist");
        }

        userDetailsDao.createUserDetails(userDetails);
    }

    // Update user details (Accessible by ROLE_USER or ROLE_ADMIN)
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void updateUserDetails(@PathVariable int userId, @RequestBody UserDetails userDetails) {
        // Ensure the user exists
        User user = userDao.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Ensuring user details exist
        UserDetails existingDetails = userDetailsDao.getUserDetailsByUserId(userId);
        if (existingDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User details not found");
        }

        // Ensuring the userId in path and body match
        userDetails.setUserId(userId);
        userDetailsDao.updateUserDetails(userDetails);
    }

    // Delete user details (Accessible by ROLE_ADMIN)
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUserDetails(@PathVariable int userId) {
        // Ensuring the user exists
        User user = userDao.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Ensuring user details exist
        UserDetails existingDetails = userDetailsDao.getUserDetailsByUserId(userId);
        if (existingDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User details not found");
        }

        userDetailsDao.deleteUserDetails(userId);
    }
}
