package com.danielbraga.workflow.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.danielbraga.workflow.repository.CountryRepository;

import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrepareTripController {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TaskService taskService;
    
    @GetMapping("/preparetrip")
    public String prepareTrip(@RequestParam(name = "taskId", required = true) String taskId, @RequestParam(name = "callbackUrl", required = true) String callbackUrl, Model model) {

        model.addAttribute("taskId", taskId);
        model.addAttribute("callbackUrl", callbackUrl);
        model.addAttribute("countries", countryRepository.findAll());

        return "preparetrip";
    }

    @PostMapping("/preparetrip")
    public String doPrepareTrip(
        @RequestParam(name = "taskId", required = true) String taskId, 
        @RequestParam(name = "callbackUrl", required = true) String callbackUrl,
        @RequestParam(name = "money", required = true) String money,
        @RequestParam(name = "country", required = true) String country,
        Model model
    ) {
        taskService.setVariable(taskId, "money", new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(new BigDecimal(money)));
        taskService.setVariable(taskId, "country", country);
        taskService.complete(taskId);

        return "redirect:" + callbackUrl;
    }
}
