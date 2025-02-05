
package com.techelevator.model;

import java.time.LocalDateTime;

public class EquipmentUsage {
    private int usageId;
    private int sessionId;
    private int exerciseId;
    private LocalDateTime dateTime;
    private Integer reps;
    private Double weightPerRep;
    private String notes;


    public EquipmentUsage() {
    }

    public EquipmentUsage(int usageId, int sessionId, int exerciseId, LocalDateTime dateTime, Integer reps, Double weightPerRep, String notes) {
        this.usageId = usageId;
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
        this.dateTime = dateTime;
        this.reps = reps;
        this.weightPerRep = weightPerRep;
        this.notes = notes;
    }
    public int getUsageId() {
        return usageId;
    }

    public void setUsageId(int usageId) {
        this.usageId = usageId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Double getWeightPerRep() {
        return weightPerRep;
    }

    public void setWeightPerRep(Double weightPerRep) {
        this.weightPerRep = weightPerRep;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
