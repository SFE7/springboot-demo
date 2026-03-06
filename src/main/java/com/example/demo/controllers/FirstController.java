package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FirstController {

    @GetMapping("/")
    public String home(
            @RequestParam(required = false, defaultValue = "Spring Boot") String framework,@RequestParam(required = false, defaultValue = "v3.3.3") String version,
            Model model
    ) {
        model.addAttribute("myframework", framework);
        model.addAttribute("version", version);

        return "pages/home";
    }
}