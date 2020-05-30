package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Color;
import com.example.smartParking.model.domain.Division;
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
public class ColorEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("color")
    public String getColorsEdit(Model model) {
        model.addAttribute("colors", dataEditingService.getAllColors());
        return "dataEditing/color/colorList";
    }

    @GetMapping("color/add")
    public String addColor(Model model) {
        return "dataEditing/color/addColor";
    }

    @PostMapping("color/add")
    public String addColor(@Valid Color color, BindingResult bindingResult, Model model) {
        if (dataEditingService.addColor(color, bindingResult, model)) {
            return getColorsEdit(model);
        } else return addColor(model);
    }

    @GetMapping("color/edit/{color}")
    public String editColor(@PathVariable Color color, Model model) {
        model.addAttribute("color", color);
        return "dataEditing/color/editColor";
    }

    @PostMapping("color/edit/{colorId}")
    public String editColor(@PathVariable Long colorId, @Valid Color changedColor, BindingResult bindingResult, Model model) {
        Optional<Color> color = dataEditingService.getColor(colorId);
        if (color.isEmpty()) {
            model.addAttribute("message", "Такого цвета не существует");
            return getColorsEdit(model);
        }
        boolean success = dataEditingService.updateColor(colorId, changedColor.getName(), bindingResult, model);;
        if (success) {
            return getColorsEdit(model);
        }
        else return editColor(color.get(), model);

    }

    @GetMapping("color/delete/{colorId}")
    public String deleteColor(@PathVariable Long colorId, Model model) {
        dataEditingService.deleteColor(colorId, model);
        return getColorsEdit(model);
    }
}
