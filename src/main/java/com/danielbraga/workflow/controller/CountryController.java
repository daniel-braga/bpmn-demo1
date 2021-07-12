package com.danielbraga.workflow.controller;

import javax.validation.Valid;

import com.danielbraga.workflow.model.Country;
import com.danielbraga.workflow.repository.CountryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CountryController {
    
    @Autowired
    private CountryRepository repository;

    @GetMapping("/country/add")
    public String addForm(Model model) {
        model.addAttribute("country", new Country());
        return "country/addform";
    }

    @PostMapping("/country/add")
    public ResponseEntity<Country> countrySubmit(@ModelAttribute @Valid Country country, Model model) {
        return new ResponseEntity<Country>(repository.save(country), HttpStatus.CREATED);
    }
}
