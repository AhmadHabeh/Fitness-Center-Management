
package com.techelevator.model;

public class UserDetails {
    private int userId;
    private String name;
    private String email;
    private String photo;
    private String workoutGoals;
    private String workoutProfile;

    public UserDetails() {
    }

    public UserDetails(int userId, String name, String email, String photo, String workoutGoals, String workoutProfile) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.workoutGoals = workoutGoals;
        this.workoutProfile = workoutProfile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getWorkoutGoals() {
        return workoutGoals;
    }

    public void setWorkoutGoals(String workoutGoals) {
        this.workoutGoals = workoutGoals;
    }

    public String getWorkoutProfile() {
        return workoutProfile;
    }

    public void setWorkoutProfile(String workoutProfile) {
        this.workoutProfile = workoutProfile;
    }
}
