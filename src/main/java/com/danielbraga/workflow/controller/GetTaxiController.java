package com.danielbraga.workflow.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetTaxiController {

    @Autowired
    private TaskService taskService;
    
    @GetMapping("/gettaxi")
    public String getTaxi(@RequestParam(name = "taskId", required = true) String taskId, @RequestParam(name = "callbackUrl", required = true) String callbackUrl, Model model) {

        model.addAttribute("taskId", taskId);
        model.addAttribute("callbackUrl", callbackUrl);
        model.addAttribute("country", (String) taskService.getVariable(taskId, "country"));
        model.addAttribute("money", (String) taskService.getVariable(taskId, "money"));

        return "gettaxi";
    }

    @PostMapping("/gettaxi")
    public String doGetTaxi(
        @RequestParam(name = "taskId", required = true) String taskId, 
        @RequestParam(name = "callbackUrl", required = true) String callbackUrl,
        @RequestParam(name = "taxiCost", required = true) String taxiCost,
        Model model
    ) {
        taskService.setVariable(taskId, "taxiCost", new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(new BigDecimal(taxiCost)));
        taskService.complete(taskId);

        return "redirect:" + callbackUrl;
    }
}
