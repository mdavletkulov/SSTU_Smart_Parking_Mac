package com.example.smartParking.controllers.edit;

import com.example.smartParking.service.DataEditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/dataEdit")
public class DataEditingController {

    @GetMapping
    public String getMainPage() {
        return "dataEditing/chooseData";
    }
}
