package com.example.Poller.service;

import com.example.Poller.domain.*;
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
import java.util.*;
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

    private static final String numFormat = "^[a-z,A-Z]\\d{3}[a-z,A-Z]{2}.*";

    public void deleteLastDateEvents(Long parkingId) {
        List<Event> events = eventRepo.getOldEventsWithPhotos(parkingId);
        for (Event event : events) {
            String fileName = event.getPhotoName();
            File file = new File(uploadPath + "/autos/" + fileName);
            if (file.delete()) {
                event.setPhotoName(null);
                eventRepo.save(event);
            }
        }
    }

    public void processPollEvent(String resultFileName, List<ParkingNumberEvent> placeNumbers, Long parkingId) {
        List<String> autoNums = automobileRepo.findAllNums();
        List<String> noRegionNums = new ArrayList<>();
        for (String num : autoNums) {
            noRegionNums.add(num.substring(0, 6));
        }
        for (ParkingNumberEvent parkingNumberEvent : placeNumbers) {
            if (parkingNumberEvent.getNumbers() != null && parkingNumberEvent.getNumbers().isEmpty()) {
                processEmptyParking(parkingId, parkingNumberEvent.getPlaceNum());
            } else if (parkingNumberEvent.getNumbers() != null) {
                processAutoEvents(parkingNumberEvent, parkingId, noRegionNums);
            }
        }
        File file = new File(uploadPath + "/autos/" + resultFileName);
        file.delete();
    }

    List<String> checkAndProcessImage(String resultFileName) {
        if (resultFileName != null && !resultFileName.isBlank()) {
            return processImage(resultFileName);
        }
        return Collections.emptyList();
    }

    private void processAutoEvents(ParkingNumberEvent parkingNumberEvent, Long parkingId, List<String> noRegionNums) {
        Map<String, Boolean> readNums = new HashMap<>();
        readNums = processNumber(parkingNumberEvent.getNumbers(), noRegionNums);
        String number = null;
        for (Map.Entry<String, Boolean> num : readNums.entrySet()) {
            if (num.getValue()) {
                number = num.getKey();
                break;
            }
        }
        if (number == null) {
            processUnknownAuto(parkingNumberEvent, parkingId);
        } else {
            processKnownAuto(parkingNumberEvent, number, parkingId);
        }
    }

    private void processKnownAuto(ParkingNumberEvent parkingNumberEvent, String number, Long parkingId) {
        Optional<Event> activePlaceEvent = eventRepo.findActiveParkingEventByPlaceNum(parkingId, parkingNumberEvent.getPlaceNum());
        Optional<Event> activeAutoEvent = eventRepo.findActiveAutoEvent(number);
        Optional<Event> activeAutoPlaceEvent = eventRepo.findActiveAutoPlaceEvent(number, parkingId, parkingNumberEvent.getPlaceNum());
        if (activeAutoPlaceEvent.isPresent()) {
            return;
        }
        if (activePlaceEvent.isPresent()) {
            Event activeEvent = activePlaceEvent.get();
            stopEvent(activeEvent);
        }
        if (activeAutoEvent.isPresent()) {
            Event activeEvent = activeAutoEvent.get();
            stopEvent(activeEvent);
        }
        Event event = new Event();
        Optional<Place> place = placeRepo.findPlace(parkingNumberEvent.getPlaceNum(), parkingId);
        if (place.isPresent()) {
            Optional<Automobile> automobile = automobileRepo.findByNumber(number);
            if (automobile.isPresent()) {
                Date date = new Date();
                long time = date.getTime();
                event.setStartTime(new Timestamp(time));
                event.setEndTime(null);
                event.setAutoViolation(false);
                event.setPhotoName(parkingNumberEvent.getFileName());
                event.setPlace(place.get());
                event.setAutomobile(automobile.get());
                eventRepo.save(event);
            }
        }
    }

    private void processUnknownAuto(ParkingNumberEvent parkingNumberEvent, Long parkingId) {
        Event event = new Event();
        Optional<Place> place = placeRepo.findPlace(parkingNumberEvent.getPlaceNum(), parkingId);
        if (place.isPresent()) {
            Date date = new Date();
            long time = date.getTime();
            event.setStartTime(new Timestamp(time));
            event.setEndTime(null);
            event.setAutoViolation(true);
            event.setPhotoName(parkingNumberEvent.getFileName());
            event.setPlace(place.get());
            eventRepo.save(event);
        }
    }

    private void processEmptyParking(Long parkingId, Integer placeNum) {
        Optional<Event> activeEvent = eventRepo.findActiveParkingEventByPlaceNum(parkingId, placeNum);
        activeEvent.ifPresent(this::stopEvent);
    }

    private void stopEvent(Event event) {
        Date date = new Date();
        long time = date.getTime();
        event.setEndTime(new Timestamp(time));
        eventRepo.save(event);
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

    public static HashMap<String, Boolean> processNumber(List<String> dbNumbers, List<String> neuralNumbers) {
        if (dbNumbers == null || neuralNumbers == null) {
            throw new IllegalArgumentException("Null arguments passed");
        }

        HashMap<String, Boolean> result = new HashMap<>();
        for (String neuralNum : neuralNumbers) {
            if (neuralNum.length() < 6 || !neuralNum.matches(numFormat)) {
                continue;
            }
            String finalNeuralNum = neuralNum.substring(0, 6).toUpperCase();

            int minHamming = 100;
            String dbMatched = "";
            for (String dbNum : dbNumbers) {
                int currentHamming = hammingDistance(dbNum, finalNeuralNum);
                if (currentHamming < minHamming) {
                    minHamming = currentHamming;
                    dbMatched = dbNum;
                }
            }

            if (minHamming < 4) {
                result.put(dbMatched, true);
            } else {
                result.put(finalNeuralNum, false);
            }
        }

        return result;
    }

    private static int hammingDistance(String one, String two) {
        int counter = 0;

        for (int i = 0; i < one.length(); i++) {
            if (one.charAt(i) != two.charAt(i)) counter++;
        }

        return counter;
    }

}
