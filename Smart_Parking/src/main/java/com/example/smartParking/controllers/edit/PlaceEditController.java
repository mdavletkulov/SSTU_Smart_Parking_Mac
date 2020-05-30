package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Parking;
import com.example.smartParking.model.domain.Person;
import com.example.smartParking.model.domain.Place;
import com.example.smartParking.model.domain.Subdivision;
import com.example.smartParking.service.DataEditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/dataEdit")
public class PlaceEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("place")
    public String getPlacesEdit(Model model) {
        model.addAttribute("places", dataEditingService.getAllPlaces());
        return "dataEditing/place/placeList";
    }

    @GetMapping("place/add")
    public String addPlace(Model model) {
        model.addAttribute("parkings", dataEditingService.getAllParking());
        return "dataEditing/place/addPlace";
    }

    @PostMapping("place/add")
    public String addPlace(@Valid Place place, BindingResult bindingResult, @RequestParam String placeNumber, Model model) {
        if (!placeNumber.matches("^[0-9]*$")) {
            model.addAttribute("placeNumberError", "Номер парковочного места может содержать только цифры");
            return addPlace(model);
        }
        if (dataEditingService.addPlace(place, bindingResult, model)) {
            return getPlacesEdit(model);
        } else return addPlace(model);
    }

    @GetMapping("place/edit/{place}")
    public String editPlace(@PathVariable Place place, Model model) {
        model.addAttribute("place", place);
        model.addAttribute("parkings", dataEditingService.getAllParking());
        return "dataEditing/place/editPlace";
    }

    @PostMapping("place/edit/{placeId}")
    public String editPlace(@PathVariable Long placeId, @Valid Place changedPlace,  BindingResult bindingResult, @RequestParam(required = false) String placeNumber, Model model) {
        Optional<Place> place = dataEditingService.getPlace(placeId);
        if (place.isEmpty()) {
            model.addAttribute("message", "Такого парковочного места не существует");
            return getPlacesEdit(model);
        }
        if (!placeNumber.matches("^[0-9]*$")) {
            model.addAttribute("placeNumberError", "Номер парковочного места может содержать только цифры");
            return editPlace(place.get(), model);
        }
        boolean success = dataEditingService.updatePlace(placeId, changedPlace, bindingResult, model);
        if (success) {
            return getPlacesEdit(model);
        }
        else return editPlace(place.get(), model);
    }

    @GetMapping("place/delete/{placeId}")
    public String deletePerson(@PathVariable Long placeId, Model model) {
        dataEditingService.deletePlace(placeId, model);
        return getPlacesEdit(model);
    }
}
