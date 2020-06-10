package com.example.smartParking.service;

import com.example.smartParking.model.ReportEntity;
import com.example.smartParking.model.domain.Event;
import com.example.smartParking.model.domain.Person;
import com.example.smartParking.model.domain.TypeJobPosition;
import com.example.smartParking.repos.DivisionRepo;
import com.example.smartParking.repos.EventRepo;
import com.example.smartParking.repos.JobPositionRepo;
import com.example.smartParking.repos.SubdivisionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    ReportCreatorService reportCreatorService;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    DivisionRepo divisionRepo;

    @Autowired
    SubdivisionRepo subdivisionRepo;

    @Autowired
    JobPositionRepo jobPositionRepo;

    public Timestamp convertToTimestamp(String dateTime) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                .appendOptional(DateTimeFormatter.ofPattern(("dd.MM.yyyy H:mm")))
                .toFormatter();
        LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(dateTime));
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss"));
        return Timestamp.valueOf(localDateTime);
    }

    public List<ReportEntity> createReportEntities(List<Event> events) {
        List<ReportEntity> reportEntities = new ArrayList<>();
        for (Event event : events) {
            Person person = new Person();
            ReportEntity reportEntity = new ReportEntity();
            if (event.getAutomobile() != null) {
                reportEntity.setAutoNum(event.getAutomobile().getNumber());
                reportEntity.setAutoModel(event.getAutomobile().getModel());
            }
            else if (event.getUnknownNum() != null && !event.getUnknownNum().isBlank()) {
                reportEntity.setAutoNum(event.getUnknownNum());
            }
            if (event.getPerson() != null) {
                person = event.getPerson();
                reportEntity.setPersonName(person.getFullName());
                reportEntity.setEmployee(event.getPerson().isEmployee());
                reportEntity.setStudent(event.getPerson().isStudent());
            }
            if (event.getPlace() != null) {
                reportEntity.setParkingName(event.getPlace().getParking().getDescription());
                reportEntity.setPlaceNum(event.getPlace().getPlaceNumber());
            }
            reportEntity.setStartTime(event.getStartTime());
            reportEntity.setEndTime(event.getEndTime());

            if (person.getCourse() != null) reportEntity.setCourse(person.getCourse().toString());
            if (person.getCourse() != null) reportEntity.setGroup(person.getGroupName());
            if (person.getSubdivision() != null) {
                reportEntity.setDivision(person.getSubdivision().getDivision().getName());
                reportEntity.setSubdivision(person.getSubdivision().getName());
            }
            if (person.getJobPosition() != null) reportEntity.setJobPosition(person.getJobPosition().getNamePosition());
            if (event.getPassNumViolation()) reportEntity.setPassViolation("Пропуск просрочен или не действителен!");
            if (event.getAutoViolation()) {
                reportEntity.setAutoViolation("Неизвестный автомобиль!");
            }
            if (event.getPersonViolation()) {
                reportEntity.setPersonViolation("Неизвестный водитель!");
            }
//            if (event.getStatusViolation())
//                reportEntity.setStatusViolation("Нарушение правил парковки на место для людей со специальным статусом!");
            reportEntities.add(reportEntity);
        }
        return reportEntities;
    }

    public void createReport(List<Event> events, Model model) {
        if (!events.isEmpty()) {
            List<ReportEntity> unSortReportEntities = createReportEntities(events);
            List<ReportEntity> reportEntities = unSortReportEntities.stream()
                    .sorted(Comparator.comparing(ReportEntity::getPersonName,
                            Comparator.nullsLast(String::compareTo))
                    .thenComparing(ReportEntity::getStartTime, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
            boolean reportSaved = reportCreatorService.createDocxReport(reportEntities, model);
            if (reportSaved) {
                model.addAttribute("messageType", "success");
                model.addAttribute("message", "Отчет успешно сохранен");
                model.addAttribute("success", "yes");
            } else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Произошла ошибка при создании отчета!");
                model.addAttribute("noSuccess", "no");
            }
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "По выбранным критериям нет парковочных событий");
            model.addAttribute("noSuccess", "no");
        }
    }

    public List<Event> findCommonEvents(String typeJob, String division, String subdivision,
                                        String startTime, String endTime,
                                        Boolean student, Boolean employee, Boolean violation) {
        List<Event> events = new ArrayList<>();
        Timestamp startDateTime = convertToTimestamp(startTime);
        Timestamp endDateTime = convertToTimestamp(endTime);
        if (student) {
            if (subdivision != null && !subdivision.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesBySubdivisionStudent(startDateTime, endDateTime, subdivision);
                if (violation) events = findOnlyViolation(events);
            } else if (division != null && !division.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesByDivisionStudent(startDateTime, endDateTime, division);
                if (violation) events = findOnlyViolation(events);
            } else {
                events = eventRepo.findAllParkingBetweenDatesStudent(startDateTime, endDateTime);
                if (violation) events = findOnlyViolation(events);
            }
        } else if (employee) {
            if (subdivision != null && !subdivision.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesBySubdivisionEmployee(startDateTime, endDateTime, subdivision);
                if (violation) events = findOnlyViolation(events);
            } else if (typeJob != null && !typeJob.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesByJobType(startDateTime, endDateTime, typeJob);
                if (violation) events = findOnlyViolation(events);
            } else if (division != null && !division.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesByDivisionEmployee(startDateTime, endDateTime, division);
                if (violation) events = findOnlyViolation(events);
            } else {
                events = eventRepo.findAllParkingBetweenDatesEmployee(startDateTime, endDateTime);
                if (violation) events = findOnlyViolation(events);
            }
        } else {
            if (subdivision != null && !subdivision.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesBySubdivision(startDateTime, endDateTime, subdivision);
                if (violation) events = findOnlyViolation(events);
            } else if (division != null && !division.isBlank()) {
                events = eventRepo.findAllParkingBetweenDatesByDivision(startDateTime, endDateTime, division);
                if (violation) events = findOnlyViolation(events);
            } else {
                events = eventRepo.findAllParkingBetweenDates(startDateTime, endDateTime);
                if (violation) events = findOnlyViolationCommon(events);
            }
        }
        return events;
    }

    private List<Event> findOnlyViolation(List<Event> events) {
        return events.stream().filter(Event::isPassViolation)
                .collect(Collectors.toList());
    }

    private List<Event> findOnlyViolationCommon(List<Event> events) {
        return events.stream().filter(event -> event.getPersonViolation() || event.getAutoViolation() || event.getPassNumViolation())
                .collect(Collectors.toList());
    }

    public void addDefaultAttributes(Model model) {
        List<String> list = new ArrayList<>();
        List<TypeJobPosition> list1 = new ArrayList<>();
        list1.addAll(subdivisionRepo.findAllTypeJobPosition());
        for (TypeJobPosition jobPosition : list1) {
            list.add(jobPosition.getType());
        }
        model.addAttribute("typesJob", list);
        model.addAttribute("divisions", divisionRepo.findAll());
    }

    public boolean checkDates(String startTime, String endTime, Model model) {
        boolean correct = true;
        if (startTime == null || startTime.isBlank()) {
            model.addAttribute("startTimeError", "Введите начальную дату!");
            correct = false;
        }
        if (endTime == null || endTime.isBlank()) {
            model.addAttribute("endTimeError", "Введите конечную дату!");
            correct = false;
        }
        return correct;
    }

    public Boolean checkStudent(String student, String employee) {
        boolean studentPresent = false;
        if (student != null && !student.isBlank() && (employee == null || employee.isBlank())) {
            studentPresent = true;
        }
        return studentPresent;
    }

    public Boolean checkEmployee(String student, String employee) {
        boolean employeePresent = false;
        if (employee != null && !employee.isBlank() && (student == null || student.isBlank())) {
            employeePresent = true;
        }
        return employeePresent;
    }

    public Boolean checkViolation(String violation) {
        boolean violationPresent = false;
        if (violation != null && !violation.isBlank()) {
            violationPresent = true;
        }
        return violationPresent;
    }

    public List<Event> findParkingEvents(String startTime, String endTime, Long parkingId) {
        Timestamp startDateTime = convertToTimestamp(startTime);
        Timestamp endDateTime = convertToTimestamp(endTime);
        return eventRepo.findAllParkingEventsBetweenDates(startDateTime, endDateTime, parkingId);
    }

    public List<Event> findSimpleEvents(String startTime, String endTime, String personId) {
        Timestamp startDateTime = convertToTimestamp(startTime);
        Timestamp endDateTime = convertToTimestamp(endTime);
        if (personId != null && !personId.isBlank()) {
            Long personIdL = Long.valueOf(personId);
            return eventRepo.findAllPersonParkingBetweenDates(startDateTime, endDateTime, personIdL);
        }
        return eventRepo.findAllParkingBetweenDates(startDateTime, endDateTime);
    }
}
