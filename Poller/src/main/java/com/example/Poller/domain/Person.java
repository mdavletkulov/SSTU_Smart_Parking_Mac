package com.example.Poller.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Поле имени не может быть пустым")
    @Length(max = 60, message = "Значение имени слишком длинное")
    private String firstName;
    @NotBlank(message = "Поле фамилии не может быть пустым")
    @Length(max = 60, message = "Значение фамилии слишком длинное")
    private String secondName;
    @Length(max = 60, message = "Значение отчества слишком длинное")
    private String middleName;
    @ManyToOne
    @JoinColumn(name = "job_position")
    private JobPosition jobPosition;
    @ManyToOne
    @JoinColumn(name = "subdivision")
    private Subdivision subdivision;
    @ManyToOne
    @JoinColumn(name = "division")
    private Division division;
    @NotNull(message = "Водитель должен иметь какой-либо статус")
    private boolean employee;
    @NotNull(message = "Водитель должен иметь какой-либо статус")
    private boolean student;
    @Digits(message = "Поле группы может содержать только одно целое число", integer = 1, fraction = 0)
    private Integer course;
    @Length(max = 10, message = "Значение группы слишком длинное")
    private String groupName;
    @Digits(message = "Номер пропуска может содержать только целые числа", integer = 10, fraction = 0)
    private Integer passNum;
    private Date passEndDate;
    @NotNull(message = "Водитель должен иметь или не иметь специального статуса")
    private boolean specialStatus;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getPassNum() {
        if (getPassEndDate() == null || getPassEndDate().getTime() < new Date().getTime()) {
            passNum = null;
            return null;
        }
        return passNum;
    }

    public String getPassNumString() {
        if (passNum != null) return getPassNum().toString();
        else return null;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Date getPassEndDate() {
        if (passNum == null) return null;
        else return passEndDate;
    }

    public void setPassEndDate(Date passEndDate) {
        this.passEndDate = passEndDate;
    }

    public boolean isSpecialStatus() {
        return specialStatus;
    }

    public void setSpecialStatus(boolean specialStatus) {
        this.specialStatus = specialStatus;
    }

    public String getFullName() {
        return secondName + " " + firstName + " " + middleName;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public String getStringPassEndDate() {
        Date date = passEndDate;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
