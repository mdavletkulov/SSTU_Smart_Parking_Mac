package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.JobPosition;
import com.example.smartParking.model.domain.Parking;
import com.example.smartParking.model.domain.Place;
import com.example.smartParking.model.domain.Subdivision;
import com.example.smartParking.service.DataEditingService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/dataEdit")
public class ParkingEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("parking")
    public String getParkingsEdit(Model model) {
        List<Parking> parkings = Lists.newArrayList(dataEditingService.getAllParking());
        model.addAttribute("parkings", parkings);
        Map<Long, Integer> placeNumbers = new HashMap<>();
        for (Parking parking : parkings) {
            placeNumbers.put(parking.getId(), dataEditingService.getPlaceNumbersOfParking(parking.getId()));
        }
        model.addAttribute("placeNumbers", placeNumbers);
        return "dataEditing/parking/parkingList";
    }

    @GetMapping("parking/add")
    public String addParking(Model model) {
        return "dataEditing/parking/addParking";
    }

    @PostMapping("parking/add")
    public String addParking(@Valid Parking parking,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam("image") MultipartFile image) throws IOException {
        if (dataEditingService.addParking(parking, bindingResult, model, image)) {
            return getParkingsEdit(model);
        } else return addParking(model);
    }

    @GetMapping("parking/edit/{parking}")
    public String editParking(@PathVariable Parking parking, Model model) {
        model.addAttribute("parking", parking);
        return "dataEditing/parking/editParking";
    }

    @PostMapping("parking/edit/{parkingId}")
    public String editParking(@PathVariable Long parkingId,
                              @Valid Parking changedParking,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam("image") MultipartFile image) throws IOException {
        Optional<Parking> parking = dataEditingService.getParking(parkingId);
        if (parking.isEmpty()) {
            model.addAttribute("message", "Такого института не существует");
            return getParkingsEdit(model);
        }
        boolean success = dataEditingService.updateParking(parkingId, changedParking, bindingResult, model, image);
        if (success) {
            return getParkingsEdit(model);
        }
        else return editParking(parking.get(), model);
    }

    @GetMapping("parking/delete/{parkingId}")
    public String deleteParking(@PathVariable Long parkingId, Model model) {
        dataEditingService.deleteParking(parkingId, model);
        return getParkingsEdit(model);
    }
}
