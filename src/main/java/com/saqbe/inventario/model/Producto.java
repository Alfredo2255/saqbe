package com.saqbe.inventario.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Lob
    @Column(name = "fotografia", columnDefinition = "BYTEA")
    @JdbcType(VarbinaryJdbcType.class) // ESTA LÍNEA ES LA SOLUCIÓN
    private byte[] fotografia;

    @Column(nullable = false)
    private Integer stock;

    public Producto() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public byte[] getFotografia() { return fotografia; }
    public void setFotografia(byte[] fotografia) { this.fotografia = fotografia; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getFotografiaBase64() {
        if (fotografia == null) return null;
        return java.util.Base64.getEncoder().encodeToString(fotografia);
    }
}
