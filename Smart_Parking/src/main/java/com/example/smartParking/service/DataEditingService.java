package com.example.smartParking.service;

import com.example.smartParking.utils.ControllerUtils;
import com.example.smartParking.model.domain.*;
import com.example.smartParking.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class DataEditingService {

    @Value("${upload.img.path}")
    private String uploadPath;

    @Autowired
    AutomobileRepo automobileRepo;

    @Autowired
    ColorRepo colorRepo;

    @Autowired
    DivisionRepo divisionRepo;

    @Autowired
    JobPositionRepo jobPositionRepo;

    @Autowired
    ParkingRepo parkingRepo;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    PlaceRepo placeRepo;

    @Autowired
    SubdivisionRepo subdivisionRepo;

    //------------------------- Color --------------------------------//

    public Iterable<Color> getAllColors() {
        return colorRepo.findAll();
    }

    public Optional<Color> getColor(Long id) {
        return colorRepo.findById(id);
    }

    public boolean updateColor(Long colorId, String colorName, BindingResult bindingResult, Model model) {
        Optional<Color> colorDB = getColor(colorId);
        boolean success = true;
        if (colorDB.isPresent()) {
            Color color = colorDB.get();
            if (ControllerUtils.hasError(model, bindingResult)) {
                success = false;
            } else {
                color.setName(colorName);
                colorRepo.save(color);
            }
        } else {
            model.addAttribute("message", "Такого цвета не существует");
            success = false;
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return success;
    }

    public void deleteColor(Long colorId, Model model) {
        Optional<Color> color = colorRepo.findById(colorId);
        if (color.isPresent()) {
            colorRepo.deleteById(colorId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Цвет не найден");
            return;
        }
        color = colorRepo.findById(colorId);
        if (color.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Цвет успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Цвет не был удален");
        }
    }

    public boolean addColor(Color color, BindingResult bindingResult, Model model) {
        Color colorFromDB = colorRepo.findByName(color.getName());

        if (colorFromDB != null) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такой цвет уже существует!");
            return false;
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        colorRepo.save(color);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", String.format("%s цвет создан!", color.getName()));
        return true;
    }


    //------------------------------------------------------------------//

    //---------------------------- Person -----------------------------//

    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    public Optional<Person> getPerson(Long id) {
        return personRepo.findById(id);
    }

    public void deletePerson(Long personId, Model model) {
        Optional<Person> person = personRepo.findById(personId);
        if (person.isPresent()) {
            personRepo.deleteById(personId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Водитель не найден");
            return;
        }
        person = personRepo.findById(personId);
        if (person.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Водитель успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Водитель не был удален");
        }

    }

    public boolean addPerson(Person person, BindingResult bindingResult, Model model, boolean correct) {
        if (person.getPassNum() != null) {
            Optional<Person> personFromDB = personRepo.findByPassNum(person.getPassNum());

            if (personFromDB.isPresent()) {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Такой пользователь уже существует");
                return false;
            }
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            if ((errors.size() == 1 && errors.containsKey("subdivisionError")) ||
                    (errors.size() == 1 && errors.containsKey("passEndDateError"))
                    || (errors.size() == 2 && errors.containsKey("passEndDateError") && errors.containsKey("subdivisionError"))) {
            } else {
                correct = false;
                model.mergeAttributes(errors);
                return false;
            }
        }
        if (correct) {
            personRepo.save(person);
            model.addAttribute("messageType", "success");
            model.addAttribute("message", String.format("Водитель %s создан!", person.getFullName()));
        }
        return correct;
    }

    public boolean updatePerson(Long personId, Person personChange, BindingResult bindingResult, Model model, boolean success) {
        Optional<Person> personDB = personRepo.findById(personId);
        if (personDB.isPresent()) {
            Person person = personDB.get();
            if (bindingResult.hasErrors()) {
                Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
                if ((errors.size() == 1 && errors.containsKey("subdivisionError")) ||
                        (errors.size() == 1 && errors.containsKey("passEndDateError"))
                        || (errors.size() == 2 && errors.containsKey("passEndDateError") && errors.containsKey("subdivisionError"))) {
                } else {
                    success = false;
                    model.mergeAttributes(errors);
                    return false;
                }
            }
            if (success) {
                person.setFirstName(personChange.getFirstName());
                person.setSecondName(personChange.getSecondName());
                person.setMiddleName(personChange.getMiddleName());
                person.setCourse(personChange.getCourse());
                person.setEmployee(personChange.isEmployee());
                person.setStudent(personChange.isStudent());
                person.setSubdivision(personChange.getSubdivision());
                person.setJobPosition(personChange.getJobPosition());
                person.setGroupName(personChange.getGroupName());
                person.setPassNum(personChange.getPassNum());
                person.setPassEndDate(personChange.getPassEndDate());
                person.setSpecialStatus(personChange.isSpecialStatus());
                personRepo.save(person);
            }
        } else {
            model.addAttribute("message", "Такого водителя не существует");
            success = false;
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return success;
    }

    public boolean validatePersonFields(Person person, String passEndDate, String passNum, String
            subdivisionName, Model model) {
        boolean correct = true;
        if (passEndDate != null && !passEndDate.isBlank()) {
            Date passDate = convertToDate(passEndDate);
            person.setPassEndDate(passDate);
        } else {
            person.setPassEndDate(null);
        }
        if (passNum != null && !passNum.isBlank()) {
            if (!passNum.matches("^[0-9]*$")) {
                model.addAttribute("passError", "Номер пропуска может содержать только цифры");
                correct = false;
            }
        }
        if (passEndDate != null && !passEndDate.isBlank() && (passNum == null || passNum.isBlank())) {
            model.addAttribute("passError", "Заполните поле номера пропуска");
            correct = false;
        }
        if ((passEndDate == null || passEndDate.isBlank()) && passNum != null && !passNum.isBlank()) {
            model.addAttribute("passDateError", "Заполните поле истечения срока пропуска");
            correct = false;
        }
        if (subdivisionName != null && !subdivisionName.isBlank()) {
            Optional<Subdivision> subdivision = subdivisionRepo.findByName(subdivisionName);
            subdivision.ifPresent(person::setSubdivision);
        }
        if (person.getGroupName() != null && person.getGroupName().isBlank()) {
            person.setGroupName(null);
        }
        if (!person.isStudent() && !person.isEmployee()) {
            model.addAttribute("statusError", "Водитель должен быть либо студентом, либо сотрудником");
            correct = false;
        }
        if (person.isStudent() && (person.getDivision() == null || person.getSubdivision() == null)) {
            model.addAttribute("divisionErr", "Студент должен относить к определенному институту и кафедре");
            correct = false;
        }
        if (person.isStudent() && ((person.getGroupName() == null || person.getGroupName().isBlank()) || (person.getCourse() == null))) {
            model.addAttribute("statusError", "Студент должен иметь группу и курс");
            correct = false;
        }
        if (person.isEmployee() && person.getJobPosition() == null) {
            model.addAttribute("statusError", "Сотрудник должен иметь должность");
            correct = false;
        }
        return correct;
    }

    //------------------------------------------------------------------//

    //--------------------------- Automobile ----------------------------//

    public List<Automobile> getAllAutos() {
        return automobileRepo.findAll();
    }

    public Optional<Automobile> getAuto(Long id) {
        return automobileRepo.findById(id);
    }

    private boolean validateNumber(String number) {
        for (char character : number.toLowerCase().toCharArray()) {
            if (!((character >= 'a' && character <= 'z') || (character >= '0' && character <= '9'))) {
                return false;
            }
        }
        return true;
    }

    public void deleteAuto(Long autoId, Model model) {
        Optional<Automobile> automobile = automobileRepo.findById(autoId);
        if (automobile.isPresent()) {
            automobileRepo.deleteById(autoId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Автомобиль не найден");
            return;
        }
        automobile = automobileRepo.findById(autoId);
        if (automobile.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Автомобиль успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Автомобиль не был удален");
        }

    }

    public boolean addAuto(Automobile automobile, BindingResult bindingResult, Model model) {
        Optional<Automobile> autoFromDB = automobileRepo.findByNumber(automobile.getNumber());

        if (autoFromDB.isPresent()) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такой автомобиль уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            if (!validateNumber(automobile.getNumber())) {
                model.addAttribute("numberError", "Номер машины должен содержать английские буквы и цифры");
            }
            if (!checkModelIsNotEmpty(automobile)) {
                model.addAttribute("modelError", "Модель машины не может быть пустой");
            }
            return false;
        }
        if (!validateNumber(automobile.getNumber())) {
            model.addAttribute("numberError", "Номер машины должен содержать английские буквы и цифры");
            return false;
        }

        if (!checkModelIsNotEmpty(automobile)) {
            model.addAttribute("modelError", "Модель машины не может быть пустой");
        }

        automobileRepo.save(automobile);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", String.format("Автомобиль %s %s создан!", automobile.getModel(), automobile.getNumber()));
        return true;
    }

    public boolean updateAuto(Long automobileId, Automobile autoChange, BindingResult bindingResult, Model
            model) {
        boolean success = true;
        Optional<Automobile> autoDB = automobileRepo.findById(automobileId);
        List<Automobile> autosFromDB = automobileRepo.findAutosByNumber(autoChange.getNumber());
        if (autoDB.isPresent()) {
            if ((autosFromDB.size() == 1 && autosFromDB.get(0).getNumber().equals(autoDB.get().getNumber())) || autosFromDB.size() == 0) {
                Automobile automobile = autoDB.get();
                if (!validateNumber(autoChange.getNumber())) {
                    model.addAttribute("numberError", "Номер машины должен содержать английские буквы и цифры");
                    success = false;
                }
                if (!checkModelIsNotEmpty(automobile)) {
                    model.addAttribute("modelError", "Модель машины не может быть пустой");
                    success = false;
                }
                if (ControllerUtils.hasError(model, bindingResult)) {
                    success = false;
                }
                if (success) {
                    automobile.setModel(autoChange.getModel());
                    automobile.setNumber(autoChange.getNumber());
                    automobile.setColor(autoChange.getColor());
                    automobile.setPerson(autoChange.getPerson());
                    automobileRepo.save(automobile);
                }
            } else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Автомобиль с таким номером уже существует");
                success = false;

            }
            if (success) {
                model.addAttribute("messageType", "success");
                model.addAttribute("message", "Успешно");
            }
        } else {
            model.addAttribute("message", "Такого автомобиля не существует");
            success = false;
        }
        return success;
    }

    private boolean checkModelIsNotEmpty(Automobile automobile) {
        return automobile.getModel() != null && !automobile.getModel().isBlank();
    }

    //------------------------------------------------------------------//

    //----------------------------- Place ------------------------------//

    public Iterable<Place> getAllPlaces() {
        return placeRepo.findAll();
    }

    public Optional<Place> getPlace(Long id) {
        return placeRepo.findById(id);
    }

    public void deletePlace(Long placeId, Model model) {
        Optional<Place> place = placeRepo.findById(placeId);
        if (place.isPresent()) {
            placeRepo.deleteById(placeId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Парковочное место не найдено");
            return;
        }
        place = placeRepo.findById(placeId);
        if (place.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Парковочное место успешно удалено");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Парковочное место не было удалено");
        }

    }

    public boolean addPlace(Place place, BindingResult bindingResult, Model model) {
        List<Place> placeFromDB = placeRepo.findByParkingAndNum(place.getPlaceNumber(), place.getParking().getId());

        if (!placeFromDB.isEmpty()) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такое парковочное место уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        placeRepo.save(place);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Парковочное место создано!");
        return true;
    }

    public boolean updatePlace(Long placeId, Place placeChange, BindingResult bindingResult, Model model) {
        Optional<Place> placeDB = placeRepo.findById(placeId);
        boolean success = true;
        if (placeDB.isPresent()) {
            Place place = placeDB.get();
            if (ControllerUtils.hasError(model, bindingResult)) {
                success = false;
            } else {
                place.setParking(placeChange.getParking());
                place.setPlaceNumber(placeChange.getPlaceNumber());
                place.setSpecialStatus(place.isSpecialStatus());
                placeRepo.save(place);
            }
        } else {
            model.addAttribute("message", "Такого парковочного места не существует");
            success = false;
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return success;
    }

    //------------------------------------------------------------------//

    //---------------------------- Division -----------------------------//

    public List<Division> getAllDivisions() {
        return divisionRepo.findAll();
    }

    public Optional<Division> getDivision(Long id) {
        return divisionRepo.findById(id);
    }

    public void deleteDivision(Long divisionId, Model model) {
        Optional<Division> division = divisionRepo.findById(divisionId);
        if (division.isPresent()) {
            divisionRepo.deleteById(divisionId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Институт не найден");
            return;
        }
        division = divisionRepo.findById(divisionId);
        if (division.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Инстиут успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Инстиут не былы удален");
        }

    }

    public boolean addDivision(Division division, BindingResult bindingResult, Model model) {
        Optional<Division> divisionFromDB = divisionRepo.findByName(division.getName());

        if (divisionFromDB.isPresent()) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такой институт уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        divisionRepo.save(division);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Институт создан!");
        return true;
    }

    public boolean updateDivision(Long divisionId, Division divisionChange, BindingResult bindingResult, Model
            model) {
        Optional<Division> divisionDB = divisionRepo.findById(divisionId);
        boolean success = true;
        if (divisionDB.isPresent()) {
            Division division = divisionDB.get();
            if (ControllerUtils.hasError(model, bindingResult)) {
                success = false;
            } else {
                division.setName(divisionChange.getName());
                divisionRepo.save(division);
            }
        } else {
            model.addAttribute("message", "Такого института не существует");
            success = false;
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return success;
    }

    //------------------------------------------------------------------//

    //------------------------------ Job -------------------------------//


    public Iterable<JobPosition> getAllJobs() {
        return jobPositionRepo.findAll();
    }

    public Optional<JobPosition> getJob(Long id) {
        return jobPositionRepo.findById(id);
    }

    public List<String> getTypeJobs() {
        return jobPositionRepo.findAllPositionNames();
    }

    public void deleteJob(Long jobId, Model model) {
        Optional<JobPosition> jobPosition = jobPositionRepo.findById(jobId);
        if (jobPosition.isPresent()) {
            jobPositionRepo.deleteById(jobId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Должность не найдена");
            return;
        }
        jobPosition = jobPositionRepo.findById(jobId);
        if (jobPosition.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Должность успешно удалена");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Должность не была удалена");
        }

    }

    public boolean addJob(JobPosition jobPosition, BindingResult bindingResult, Model model) {
        Optional<JobPosition> jobFromDB = jobPositionRepo.findByNamePosition(jobPosition.getNamePosition());

        if (jobFromDB.isPresent()) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такая должность уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        jobPositionRepo.save(jobPosition);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Должность создана!");
        return true;
    }

    public boolean updateJob(Long jobId, JobPosition jobPositionChange, BindingResult
            bindingResult, Model model) {
        Optional<JobPosition> jobDB = jobPositionRepo.findById(jobId);
        boolean success = true;
        if (jobDB.isPresent()) {
            JobPosition jobPosition = jobDB.get();
            if (ControllerUtils.hasError(model, bindingResult)) {
                success = false;
            } else {
                jobPosition.setNamePosition(jobPositionChange.getNamePosition());
                jobPosition.setTypeJobPosition(TypeJobPosition.valueOf(jobPositionChange.getTypeJobPosition()));
                jobPositionRepo.save(jobPosition);
            }
        } else {
            model.addAttribute("message", "Такого института не существует");
            success = false;
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return success;
    }

    //------------------------------------------------------------------//

    //---------------------------- Parking -----------------------------//

    public Iterable<Parking> getAllParking() {
        return parkingRepo.findAll();
    }

    public Optional<Parking> getParking(Long id) {
        return parkingRepo.findById(id);
    }

    public Integer getPlaceNumbersOfParking(Long parkingId) {
        return parkingRepo.countOfPlacesByParking(parkingId);
    }

    public void deleteParking(Long parkingId, Model model) {
        Optional<Parking> parking = parkingRepo.findById(parkingId);
        if (parking.isPresent()) {
            parkingRepo.deleteById(parkingId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Парковка не найдена");
            return;
        }
        parking = parkingRepo.findById(parkingId);
        if (parking.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Парковка успешно удалена");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Парковка не была удалена");
        }

    }

    public boolean addParking(Parking parking, BindingResult bindingResult, Model model, MultipartFile file) throws IOException {
        Optional<Parking> parkingDB = parkingRepo.findByDescription(parking.getDescription());

        if (parkingDB.isPresent()) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такая парковка уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String mimetype = file.getContentType();
            String type = mimetype.split("/")[0];
            if (!type.equals("image")) {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Загруженный файл не фото");
                return false;
            }
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/parking/" + resultFileName));
            parking.setImageName(resultFileName);
            parking.setDescription(parking.getDescription());
            parkingRepo.save(parking);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Фото не было загружено");
            return false;
        }
        parkingRepo.save(parking);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Парковка создана!");
        return true;
    }

    public boolean updateParking(Long parkingId, Parking parkingChange, BindingResult bindingResult, Model model, MultipartFile file) throws IOException {
        Optional<Parking> parkingDB = parkingRepo.findById(parkingId);
        boolean success = true;
        if (parkingDB.isPresent()) {
            Parking parking = parkingDB.get();
            if (ControllerUtils.hasError(model, bindingResult)) {
                success = false;
            }
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                String mimetype = file.getContentType();
                String type = mimetype.split("/")[0];
                if (!type.equals("image")) {
                    model.addAttribute("messageType", "danger");
                    model.addAttribute("message", "Загруженный файл не фото");
                    success = false;
                } else {
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String uuidFile = UUID.randomUUID().toString();
                    String resultFileName = uuidFile + "." + file.getOriginalFilename();

                    file.transferTo(new File(uploadPath + "/parking/" + resultFileName));
                    parking.setImageName(resultFileName);
                    parking.setDescription(parkingChange.getDescription());
                    parkingRepo.save(parking);
                }
            } else {
                model.addAttribute("messageType", "danger");
                model.addAttribute("message", "Фото не может быть пустым");
                success = false;
            }
        } else {
            model.addAttribute("message", "Такой парковки не существует");
            success = false;
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return success;
    }

    //------------------------------------------------------------------//

    //--------------------------- Subdivision ----------------------------//

    public List<Subdivision> getSubdivisionsByDivision(String divisionName) {
        return subdivisionRepo.findByDivision(divisionName);
    }

    public List<Subdivision> getAllSubdivisions() {
        return subdivisionRepo.findAll();
    }

    public Optional<Subdivision> getSubdivision(Long id) {
        return subdivisionRepo.findById(id);
    }

    public void deleteSubdivision(Long subdivisionId, Model model) {
        Optional<Subdivision> subdivision = subdivisionRepo.findById(subdivisionId);
        if (subdivision.isPresent()) {
            subdivisionRepo.deleteById(subdivisionId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Кафедра не найдена");
            return;
        }
        subdivision = subdivisionRepo.findById(subdivisionId);
        if (subdivision.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Кафедра успешно удалена");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Кафедра не была удалена");
        }

    }

    public boolean addSubdivision(Subdivision subdivision, BindingResult bindingResult, Model model) {
        Optional<Subdivision> subdivisionDB = subdivisionRepo.findByName(subdivision.getName());

        if (subdivisionDB.isPresent()) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Такая кафедра уже существует");
            return false;
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return false;
        }
        subdivisionRepo.save(subdivision);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Кафедра создана!");
        return true;
    }

    public boolean updateSubdivision(Long subdivisionId, Subdivision subdivisionChange, BindingResult
            bindingResult, Model model) {
        Optional<Subdivision> subdivisionDB = subdivisionRepo.findById(subdivisionId);
        boolean success = true;
        if (subdivisionDB.isPresent()) {
            Subdivision subdivision = subdivisionDB.get();
            if (ControllerUtils.hasError(model, bindingResult)) {
                success = false;
            } else {
                subdivision.setDivision(subdivisionChange.getDivision());
                subdivision.setName(subdivisionChange.getName());
                subdivisionRepo.save(subdivision);
            }
        } else {
            model.addAttribute("message", "Такого института не существует");
            success = false;
        }
        if (success) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return success;
    }

    //------------------------------------------------------------------//

    public Date convertToDate(String date) {
        String pattern = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.from(formatter.parse(date));
        localDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return Date.valueOf(localDate);
    }

}
