package com.techelevator.model;

import java.time.LocalDateTime;

public class ClassRegistration {
    private int registrationId;
    private int classId;
    private int userId;
    private LocalDateTime registrationTime;


    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }
    public ClassRegistration(int registrationId, int classId, int userId, LocalDateTime registrationTime) {
        this.registrationId = registrationId;
        this.classId = classId;
        this.userId = userId;
        this.registrationTime = registrationTime;
    }
    public ClassRegistration(){

    }

    @Override
    public String toString() {
        return "ClassRegistration{" +
                "registrationId=" + registrationId +
                ", classId=" + classId +
                ", userId=" + userId +
                ", registrationTime=" + registrationTime +
                '}';
    }
}
