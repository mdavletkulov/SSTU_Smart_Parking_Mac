package com.example.smartParking.service;

import com.example.smartParking.model.domain.*;
import com.example.smartParking.repos.AutomobileRepo;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.ParkingRepo;
import com.example.smartParking.repos.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ParkingService {

    @Autowired
    EventRepo eventRepo;

    @Autowired
    PlaceRepo placeRepo;

    @Autowired
    ParkingRepo parkingRepo;

    @Autowired
    AutomobileRepo automobileRepo;

     public Optional<Event> findEventById(Long eventId) {
        return eventRepo.findById(eventId);
    }

    public Iterable<Parking> findAllParking() {
        return parkingRepo.findAll();
    }

    public List<Place> findPlacesByParkingId(Long parkingId) {
        return placeRepo.findByParkingId(parkingId);
    }

    public Map<String, ?> getParkingEventAttributes(Model model, Place place) {
        Event event = eventRepo.findActivePlaceEvent(place.getId()).get();
        Map<String, Object> attributes = new HashMap<>();
        if (event.getAutomobile() != null) {
            Automobile automobile = event.getAutomobile();
            attributes.put("automobile", automobile);
            if (event.getPerson() != null) {
                Person person = event.getPerson();
                attributes.put("person", person);
            }
        }
//        attributes.put("violation", event.getStatusViolation());
        attributes.put("personViolation", event.getPersonViolation());
        attributes.put("autoViolation", event.getAutoViolation());
        attributes.put("parkingEvent", event);
        return attributes;
    }

    public List<Event> getActualEvents(Long parkingId) {
        return eventRepo.findActiveParkingEvent(parkingId);
    }

    public Iterable<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public void processStopEvent(String eventId, Model model) {
        if (eventId != null && !eventId.isBlank()) {
            Optional<Event> event = findEventById(Long.valueOf(eventId));
            if (event.isPresent()) {
                Event event1 = event.get();
                if (event1.getEndTime() != null) {
                    model.addAttribute("messageType", "danger");
                    model.addAttribute("message", "Парковочное событие уже было завершено");
                } else {
                    Date date = new Date();
                    long time = date.getTime();
                    event1.setEndTime(new Timestamp(time));
                    eventRepo.save(event1);
                    model.addAttribute("messageType", "success");
                    model.addAttribute("message", "Парковочное событие было завершено");
                }
            } else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Такое парковочное событие не найдено");
            }
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такое парковочное событие не найдено");
        }
    }
}
