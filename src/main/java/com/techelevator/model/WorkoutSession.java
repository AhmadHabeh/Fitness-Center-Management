package com.techelevator.model;

import java.time.LocalDateTime;

public class WorkoutSession {
    private int sessionId;
    private int userId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Integer checkedInBy;


    public WorkoutSession() {
    }

    public WorkoutSession(int sessionId, int userId, LocalDateTime checkInTime, LocalDateTime checkOutTime, Integer checkedInBy) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.checkedInBy = checkedInBy;
    }


    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Integer getCheckedInBy() {
        return checkedInBy;
    }

    public void setCheckedInBy(Integer checkedInBy) {
        this.checkedInBy = checkedInBy;
    }

    // Optional: Override toString() for debugging
    @Override
    public String toString() {
        return "WorkoutSession{" +
                "sessionId=" + sessionId +
                ", userId=" + userId +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", checkedInBy=" + checkedInBy +
                '}';
    }
}
