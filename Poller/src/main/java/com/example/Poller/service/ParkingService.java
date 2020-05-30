package com.example.Poller.service;

import com.example.Poller.domain.Automobile;
import com.example.Poller.domain.CarNumberResponse;
import com.example.Poller.domain.Event;
import com.example.Poller.domain.Place;
import com.example.Poller.repos.AutomobileRepo;
import com.example.Poller.repos.EventRepo;
import com.example.Poller.repos.PlaceRepo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    @Autowired
    EventRepo eventRepo;

    @Autowired
    PlaceRepo placeRepo;

    @Autowired
    AutomobileRepo automobileRepo;

    @Autowired
    @Qualifier(value = "defaultRestTemplate")
    private RestTemplate restTemplate;

    @Value("${neuralNetwork.address}")
    private String neuralNetworkAddress;

    @Value("${upload.img.path}")
    private String uploadPath;

    public void processPollEvent(String resultFileName, Long parkingId) {
        List<String> autoNums = checkAndProcessImage(resultFileName);
        if (autoNums != null && autoNums.isEmpty()) {
            processEmptyParking(parkingId);
        } else if (autoNums != null) {
            processAutoEvents(autoNums, parkingId);
        }
        File file = new File(uploadPath + "/autos/" + resultFileName);
        file.delete();
    }

    private List<String> checkAndProcessImage(String resultFileName) {
        if (resultFileName != null && !resultFileName.isBlank()) {
            return processImage(resultFileName);
        }
        return null;
    }

    private void processAutoEvents(List<String> autoNums, Long parkingId) {
        removePastEvents(autoNums, parkingId);
        List<Event> activeEvents = eventRepo.findActiveParkingEvent(parkingId);
        getOnlyNewEvents(autoNums, activeEvents);
        sortAndCreateEvents(autoNums, parkingId);
    }

    private void processEmptyParking(Long parkingId) {
        List<Event> activeEvents = eventRepo.findActiveParkingEvent(parkingId);
        for (Event event : activeEvents) {
            Date date = new Date();
            long time = date.getTime();
            event.setEndTime(new Timestamp(time));
            eventRepo.save(event);
        }
    }

    private void removePastEvents(List<String> autoNums, Long parkingId) {
        List<Event> activeWithoutAuto = eventRepo.findActiveParkingEventWithoutAuto(parkingId);
        for (Event eventWithoutAuto : activeWithoutAuto) {
            Date date = new Date();
            long time = date.getTime();
            eventWithoutAuto.setEndTime(new Timestamp(time));
            eventRepo.save(eventWithoutAuto);
        }
        List<Event> activeEvents = eventRepo.findActiveParkingEvent(parkingId);
        List<String> activeAutoNums = activeEvents.stream()
                .map(event -> event.getAutomobile().getNumber())
                .collect(Collectors.toList());
        List<String> inactiveAutoNums = (List<String>) CollectionUtils.subtract(activeAutoNums, autoNums);
        for (String number : inactiveAutoNums) {
            Optional<Event> event = eventRepo.findActiveAutoEvent(number);
            if (event.isPresent()) {
                Date date = new Date();
                long time = date.getTime();
                event.get().setEndTime(new Timestamp(time));
                eventRepo.save(event.get());
            }
        }
    }

    private void getOnlyNewEvents(List<String> autoNums, List<Event> activeEvents) {
        for (Event event : activeEvents) {
            autoNums.removeIf(num -> event.getAutomobile().getNumber().equals(num));
        }
    }

    private void sortAndCreateEvents(List<String> autoNums, Long parkingId) {
        for (String autoNum : autoNums) {
            Optional<Automobile> auto = automobileRepo.findByNumber(autoNum);
            if (auto.isPresent()) {
                createEvent(auto.get(), parkingId);
            } else {
                Automobile newAuto = new Automobile();
                newAuto.setNumber(autoNum);
                automobileRepo.save(newAuto);
                createAnonymousEvent(newAuto, parkingId);
            }
        }
    }

    private void createEvent(Automobile automobile, Long parkingId) {
        List<Place> freePlaces = placeRepo.findFreePlacesByParking(parkingId);
        if (!freePlaces.isEmpty()) {
            Event event = new Event();
            event.setAutomobile(automobile);
            event.setPlace(freePlaces.get(0));
            Date date = new Date();
            long time = date.getTime();
            event.setStartTime(new Timestamp(time));
            event.setEndTime(null);
            eventRepo.save(event);
        }
    }

    private void createAnonymousEvent(Automobile automobile, Long parkingId) {
        List<Place> freePlaces = placeRepo.findFreePlacesByParking(parkingId);
        if (!freePlaces.isEmpty()) {
            Event event = new Event();
            event.setAutomobile(automobile);
            event.setPlace(freePlaces.get(0));
            Date date = new Date();
            long time = date.getTime();
            event.setStartTime(new Timestamp(time));
            event.setEndTime(null);
            event.setPassNumViolation(false);
            event.setPersonViolation(true);
            eventRepo.save(event);
        }
    }

    private List<String> processImage(String fileName) {
        ResponseEntity<CarNumberResponse> result = restTemplate.getForEntity(neuralNetworkAddress + "/" + fileName, CarNumberResponse.class);
        return result.getBody().getNumbers();
    }

}
