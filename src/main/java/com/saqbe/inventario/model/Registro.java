package com.saqbe.inventario.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "registros")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accion; // CREADO, EDITADO, ELIMINADO

    private Long productoId;

    private String productoNombre;

    private String detalle;

    private LocalDateTime fecha;

    private Integer stockAnterior;

    private Integer stockNuevo;

    public Registro() {}

    public Registro(String accion, Long productoId, String productoNombre, String detalle,
                    Integer stockAnterior, Integer stockNuevo) {
        this.accion = accion;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.detalle = detalle;
        this.stockAnterior = stockAnterior;
        this.stockNuevo = stockNuevo;
        this.fecha = LocalDateTime.now();
    }

    public String getFechaFormateada() {
        if (fecha == null) return "";
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Integer getStockAnterior() { return stockAnterior; }
    public void setStockAnterior(Integer stockAnterior) { this.stockAnterior = stockAnterior; }
    public Integer getStockNuevo() { return stockNuevo; }
    public void setStockNuevo(Integer stockNuevo) { this.stockNuevo = stockNuevo; }
}
