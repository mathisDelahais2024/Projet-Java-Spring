package com.eni.ProjetJava.ihm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}


