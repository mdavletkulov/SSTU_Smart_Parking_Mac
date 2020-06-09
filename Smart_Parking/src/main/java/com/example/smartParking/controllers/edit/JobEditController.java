package com.example.smartParking.controllers.edit;

import com.example.smartParking.model.domain.Division;
import com.example.smartParking.model.domain.JobPosition;
import com.example.smartParking.model.domain.Parking;
import com.example.smartParking.model.domain.TypeJobPosition;
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
public class JobEditController {

    @Autowired
    DataEditingService dataEditingService;

    @GetMapping("job")
    public String getJobEdit(Model model) {
        model.addAttribute("jobs", dataEditingService.getAllJobs());
        return "dataEditing/job/jobList";
    }

    @GetMapping("job/add")
    public String addJob(Model model) {
        model.addAttribute("typeJobPositions", dataEditingService.getTypeJobs());
        return "dataEditing/job/addJob";
    }

    @PostMapping("job/add")
    public String addJob(JobPosition job, String typeJobPosition1, Model model) {
//        if (typeJobPosition1.equals("ППС")) job.setTypeJobPosition(TypeJobPosition.PPS);
//        if (typeJobPosition1.equals("АУП")) job.setTypeJobPosition(TypeJobPosition.AUP);
        if (dataEditingService.addJob(job, model)) {
            return getJobEdit(model);
        } else return addJob(model);
    }

    @GetMapping("job/edit/{job}")
    public String editJob(@PathVariable JobPosition job, Model model) {
        model.addAttribute("job", job);
        model.addAttribute("typeJobPositions", dataEditingService.getTypeJobs());
        return "dataEditing/job/editJob";
    }

    @PostMapping("job/edit/{jobId}")
    public String editJob(@PathVariable Long jobId, JobPosition changedJob, String typeJobPosition1, Model model) {
        Optional<JobPosition> jobPosition = dataEditingService.getJob(jobId);
        if (jobPosition.isEmpty()) {
            model.addAttribute("message", "Такого института не существует");
            return getJobEdit(model);
        }
//        if (typeJobPosition1.equals("ППС")) changedJob.setTypeJobPosition(TypeJobPosition.PPS);
//        if (typeJobPosition1.equals("АУП")) changedJob.setTypeJobPosition(TypeJobPosition.AUP);
        boolean success = dataEditingService.updateJob(jobId, changedJob, model);
        if (success) {
            return getJobEdit(model);
        }
        else return editJob(jobPosition.get(), model);
    }

    @GetMapping("job/delete/{jobId}")
    public String deleteJob(@PathVariable Long jobId, Model model) {
        dataEditingService.deleteJob(jobId, model);
        return getJobEdit(model);
    }
}
