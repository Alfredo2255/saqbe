package com.saqbe.inventario.controller;

import com.saqbe.inventario.model.Producto;
import com.saqbe.inventario.model.Registro;
import com.saqbe.inventario.repository.ProductoRepository;
import com.saqbe.inventario.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/recursos")
public class ProductoController {

    @Autowired
    private ProductoRepository repo;

    @Autowired
    private RegistroRepository registroRepo;

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public String listar(Model model) {
        List<Producto> productos = repo.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("totalStock", productos.stream().mapToInt(Producto::getStock).sum());
        model.addAttribute("totalRegistros", registroRepo.count());
        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("accion", "Nuevo");
        return "formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto,
                          @RequestParam("archivoFoto") MultipartFile foto,
                          RedirectAttributes flash) throws IOException {

        boolean esNuevo = (producto.getId() == null);
        Integer stockAnterior = null;
        String nombreAnterior = null;
        Double precioAnterior = null;

        if (!esNuevo) {
            Optional<Producto> existing = repo.findById(producto.getId());
            if (existing.isPresent()) {
                Producto viejo = existing.get();
                stockAnterior = viejo.getStock();
                nombreAnterior = viejo.getNombre();
                precioAnterior = viejo.getPrecio();
                if (foto.isEmpty()) {
                    producto.setFotografia(viejo.getFotografia());
                }
            }
        }

        if (!foto.isEmpty()) {
            String nombreFoto = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            Path ruta = Paths.get(UPLOAD_DIR + nombreFoto);
            Files.createDirectories(ruta.getParent());
            Files.copy(foto.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
            producto.setFotografia(nombreFoto);
        }

        Producto guardado = repo.save(producto);

        if (esNuevo) {
            String detalle = "Nuevo producto creado — Precio: Q" + String.format("%.2f", guardado.getPrecio())
                           + " — Stock inicial: " + guardado.getStock() + " unidades";
            registroRepo.save(new Registro("CREADO", guardado.getId(), guardado.getNombre(),
                detalle, null, guardado.getStock()));
            flash.addFlashAttribute("mensaje", "✅ Producto \"" + guardado.getNombre() + "\" guardado correctamente.");
            flash.addFlashAttribute("tipoMensaje", "exito");
        } else {
            StringBuilder detalle = new StringBuilder("Producto actualizado");
            if (!guardado.getNombre().equals(nombreAnterior))
                detalle.append(" — Nombre cambiado de \"").append(nombreAnterior).append("\" a \"").append(guardado.getNombre()).append("\"");
            if (precioAnterior != null && !precioAnterior.equals(guardado.getPrecio()))
                detalle.append(" — Precio: Q").append(String.format("%.2f", precioAnterior)).append(" → Q").append(String.format("%.2f", guardado.getPrecio()));
            if (stockAnterior != null && !stockAnterior.equals(guardado.getStock()))
                detalle.append(" — Stock: ").append(stockAnterior).append(" → ").append(guardado.getStock()).append(" unidades");

            registroRepo.save(new Registro("EDITADO", guardado.getId(), guardado.getNombre(),
                detalle.toString(), stockAnterior, guardado.getStock()));
            flash.addFlashAttribute("mensaje", "✏️ Producto \"" + guardado.getNombre() + "\" actualizado correctamente.");
            flash.addFlashAttribute("tipoMensaje", "info");
        }

        return "redirect:/recursos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Producto> p = repo.findById(id);
        if (p.isPresent()) {
            model.addAttribute("producto", p.get());
            model.addAttribute("accion", "Editar");
            return "formulario";
        }
        return "redirect:/recursos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        Optional<Producto> p = repo.findById(id);
        p.ifPresent(producto -> {
            String detalle = "Producto eliminado — Precio era: Q" + String.format("%.2f", producto.getPrecio())
                           + " — Stock que tenía: " + producto.getStock() + " unidades";
            registroRepo.save(new Registro("ELIMINADO", producto.getId(), producto.getNombre(),
                detalle, producto.getStock(), 0));
            repo.deleteById(id);
            flash.addFlashAttribute("mensaje", "🗑️ Producto \"" + producto.getNombre() + "\" eliminado del sistema.");
            flash.addFlashAttribute("tipoMensaje", "advertencia");
        });
        return "redirect:/recursos";
    }

    @GetMapping("/historial")
    public String historial(Model model) {
        List<Registro> registros = registroRepo.findAllByOrderByFechaDesc();
        long creados   = registros.stream().filter(r -> "CREADO".equals(r.getAccion())).count();
        long editados  = registros.stream().filter(r -> "EDITADO".equals(r.getAccion())).count();
        long eliminados= registros.stream().filter(r -> "ELIMINADO".equals(r.getAccion())).count();
        model.addAttribute("registros", registros);
        model.addAttribute("totalAcciones", registros.size());
        model.addAttribute("creados", creados);
        model.addAttribute("editados", editados);
        model.addAttribute("eliminados", eliminados);
        return "historial";
    }
}
