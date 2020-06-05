package com.example.Poller.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "parking_event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "automobile_id")
    private Automobile automobile;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;
    @NotNull(message = "Время начала парковки не может быть пустым")
    private Timestamp startTime;
    private Timestamp endTime;
    private Boolean statusViolation;
    private Boolean passNumViolation;
    private Boolean personViolation;
    private Boolean autoViolation;
    private String photoName;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Automobile getAutomobile() {
        return automobile;
    }

    public void setAutomobile(Automobile automobile) {
        this.automobile = automobile;
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

    public Boolean isPassViolation() {
        if (automobile != null && automobile.getPerson() != null) {
            return automobile.getPerson().getPassNum() == null
                    || automobile.getPerson().getPassEndDate().getTime() < getStartTime().getTime();
        } else return null;
    }

    public Boolean isSpecialStatusViolation() {
        if (place != null && automobile != null && automobile.getPerson() != null) {
            return place.isSpecialStatus() && !automobile.getPerson().isSpecialStatus();
        } else return null;
    }


    public Boolean getPersonViolation() {
        return automobile == null || automobile.getPerson() == null;
    }

    public void setPersonViolation(Boolean personViolation) {
        this.personViolation = personViolation;
    }

    public Boolean getAutoViolation() {
        return automobile == null;
    }

    public void setAutoViolation(Boolean autoViolation) {
        this.autoViolation = autoViolation;
    }

    private String getDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY HH:mm");
        return dateFormat.format(date);
    }

    public String getStartDateString() {
        return getDateString(startTime);
    }

    public String getEndDateString() {
        if (endTime != null)
            return getDateString(endTime);
        else return null;
    }

    public void setStatusViolation(Boolean statusViolation) {
        if (isSpecialStatusViolation() != null) {
            this.statusViolation = isSpecialStatusViolation();
        } else {
            this.statusViolation = statusViolation;
        }
    }

    public Boolean getStatusViolation() {
        if (isSpecialStatusViolation() != null) {
            return isSpecialStatusViolation();
        }
        if (statusViolation == null) return true;
        return statusViolation;
    }

    public Boolean getPassNumViolation() {
        if (isPassViolation() != null) {
            return isPassViolation();
        }
        if (passNumViolation == null) return false;
        return passNumViolation;
    }

    public void setPassNumViolation(Boolean passNumViolation) {
        if (isPassViolation() != null) {
            this.passNumViolation = isPassViolation();
        } else {
            this.passNumViolation = passNumViolation;
        }

    }

    public Person getPerson() {
        if (automobile != null && automobile.getPerson() != null) {
            return automobile.getPerson();
        }
        return person;
    }

    public void setPerson(Person person) {
        if (automobile != null && automobile.getPerson() != null) {
            this.person = automobile.getPerson();
        }
        this.person = person;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
