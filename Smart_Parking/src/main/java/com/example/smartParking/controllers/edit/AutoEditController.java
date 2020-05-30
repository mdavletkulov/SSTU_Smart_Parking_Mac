package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Automobile;
import com.example.smartParking.model.domain.Color;
import com.example.smartParking.model.domain.Division;
import com.example.smartParking.model.domain.Person;
import com.example.smartParking.service.DataEditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/dataEdit")
public class AutoEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("auto")
    public String getAutosEdit(Model model) {
        model.addAttribute("automobiles", dataEditingService.getAllAutos());
        return "dataEditing/auto/autoList";
    }

    @GetMapping("auto/add")
    public String addAuto(Model model) {
        model.addAttribute("persons", dataEditingService.getAllPersons());
        model.addAttribute("automobiles", dataEditingService.getAllAutos());
        model.addAttribute("colors", dataEditingService.getAllColors());
        return "dataEditing/auto/addAuto";
    }

    @PostMapping("auto/add")
    public String addAuto(@Valid Automobile automobile, BindingResult bindingResult, Model model) {
        if (dataEditingService.addAuto(automobile, bindingResult, model)) {
            return getAutosEdit(model);
        } else return addAuto(model);
    }

    @GetMapping("auto/edit/{auto}")
    public String editAuto(@PathVariable Automobile auto, Model model) {
        model.addAttribute("automobile", auto);
        model.addAttribute("colors", dataEditingService.getAllColors());
        model.addAttribute("persons", dataEditingService.getAllPersons());
        return "dataEditing/auto/editAuto";
    }

    @PostMapping("auto/edit/{autoId}")
    public String editAuto(@PathVariable Long autoId, @Valid Automobile changedAuto, BindingResult bindingResult, Model model) {
        Optional<Automobile> auto = dataEditingService.getAuto(autoId);
        if (auto.isEmpty()) {
            model.addAttribute("message", "Такого автомобиля не существует");
            return getAutosEdit(model);
        }
        boolean success = dataEditingService.updateAuto(autoId, changedAuto, bindingResult, model);;
        if (success) {
            return getAutosEdit(model);
        }
        else return editAuto(auto.get(), model);
    }

    @GetMapping("auto/delete/{autoId}")
    public String deleteAuto(@PathVariable Long autoId, Model model) {
        dataEditingService.deleteAuto(autoId, model);
        return getAutosEdit(model);
    }
}
