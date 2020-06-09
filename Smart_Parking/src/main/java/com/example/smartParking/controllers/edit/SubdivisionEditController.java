package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.*;
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
public class SubdivisionEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("subdivision")
    public String getSubdivisionsEdit(Model model) {
        model.addAttribute("subdivisions", dataEditingService.getAllSubdivisions());
        return "dataEditing/subdivision/subdivisionList";
    }

    @GetMapping("subdivision/add")
    public String addSubdivision(Model model) {
        model.addAttribute("typeJobPositions", dataEditingService.getTypeJobs());
        model.addAttribute("divisions", dataEditingService.getAllDivisions());
        return "dataEditing/subdivision/addSubdivision";
    }

    @PostMapping("subdivision/add")
    public String addSubdivision(@Valid Subdivision subdivision, BindingResult bindingResult, String typeJobPosition1, Model model) {
        if (typeJobPosition1.equals("ППС")) subdivision.setTypeJobPosition(TypeJobPosition.PPS);
        if (typeJobPosition1.equals("АУП")) subdivision.setTypeJobPosition(TypeJobPosition.AUP);
        if (dataEditingService.addSubdivision(subdivision, bindingResult, model)) {
            return getSubdivisionsEdit(model);
        } else return addSubdivision(model);
    }

    @GetMapping("subdivision/edit/{subdivision}")
    public String editSubdivision(@PathVariable Subdivision subdivision, Model model) {
        model.addAttribute("typeJobPositions", dataEditingService.getTypeJobs());
        model.addAttribute("divisions", dataEditingService.getAllDivisions());
        model.addAttribute("subdivision", subdivision);
        return "dataEditing/subdivision/editSubdivision";
    }

    @PostMapping("subdivision/edit/{subdivisionId}")
    public String editSubdivision(@PathVariable Long subdivisionId, @Valid Subdivision changedSubdivision, BindingResult bindingResult, String typeJobPosition1, Model model) {
        Optional<Subdivision> subdivision = dataEditingService.getSubdivision(subdivisionId);
        if (subdivision.isEmpty()) {
            model.addAttribute("message", "Такого института не существует");
            return getSubdivisionsEdit(model);
        }
        if (typeJobPosition1.equals("ППС")) changedSubdivision.setTypeJobPosition(TypeJobPosition.PPS);
        if (typeJobPosition1.equals("АУП")) changedSubdivision.setTypeJobPosition(TypeJobPosition.AUP);
        boolean success = dataEditingService.updateSubdivision(subdivisionId, changedSubdivision, bindingResult, model);
        if (success) {
            return getSubdivisionsEdit(model);
        } else return editSubdivision(subdivision.get(), model);
    }

    @GetMapping("subdivision/delete/{subdivisionId}")
    public String deleteSubdivision(@PathVariable Long subdivisionId, Model model) {
        dataEditingService.deleteSubdivision(subdivisionId, model);
        return getSubdivisionsEdit(model);
    }
}
