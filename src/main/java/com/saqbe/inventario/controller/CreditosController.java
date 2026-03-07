package com.saqbe.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreditosController {

    @GetMapping("/creditos")
    public String creditos() {
        return "creditos";
    }
}
