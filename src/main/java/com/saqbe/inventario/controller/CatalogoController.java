package com.saqbe.inventario.controller;

import com.saqbe.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogoController {

    private final ProductoRepository productoRepository;

    public CatalogoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "catalogo";
    }

    @GetMapping("/recursos/impresion")
    public String vistaDeImpresion(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "vistadeimpresion";
    }
}