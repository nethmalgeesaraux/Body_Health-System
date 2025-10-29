package models;

import java.time.LocalDate;

public class HealthMetric {
    private int id;
    private int userId;
    private LocalDate date;
    private double weight;
    private int systolic;
    private int diastolic;
    private int heartRate;
    private String notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "HealthMetric{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", weight=" + weight +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
                ", heartRate=" + heartRate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
