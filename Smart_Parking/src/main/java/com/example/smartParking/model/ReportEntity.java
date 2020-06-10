package com.example.smartParking.model;

import java.sql.Timestamp;

public class ReportEntity {

    private Integer placeNum;
    private String parkingName;
    private String autoNum;
    private String autoModel;
    private String personName;
    private Timestamp startTime;
    private Timestamp endTime;
    private String division;
    private String subdivision;
    private String jobPosition;
    private String group;
    private String course;
    private String passViolation;
    private String statusViolation;
    private String autoViolation;
    private String personViolation;
    private Boolean employee;
    private Boolean student;

    public Integer getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(Integer placeNum) {
        this.placeNum = placeNum;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getAutoNum() {
        return autoNum;
    }

    public void setAutoNum(String autoNum) {
        this.autoNum = autoNum;
    }

    public String getAutoModel() {
        return autoModel;
    }

    public void setAutoModel(String autoModel) {
        this.autoModel = autoModel;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPassViolation() {
        return passViolation;
    }

    public void setPassViolation(String passViolation) {
        this.passViolation = passViolation;
    }

    public String getStatusViolation() {
        return statusViolation;
    }

    public void setStatusViolation(String statusViolation) {
        this.statusViolation = statusViolation;
    }

    public String getAutoViolation() {
        return autoViolation;
    }

    public void setAutoViolation(String autoViolation) {
        this.autoViolation = autoViolation;
    }

    public String getPersonViolation() {
        return personViolation;
    }

    public void setPersonViolation(String personViolation) {
        this.personViolation = personViolation;
    }

    public Boolean getEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
    }

    public Boolean getStudent() {
        return student;
    }

    public void setStudent(Boolean student) {
        this.student = student;
    }
}
