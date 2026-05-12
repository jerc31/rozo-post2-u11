package com.empresa.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductoRequestDTO {

    @Schema(description = "Nombre del producto", example = "Laptop HP ProBook")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Precio en pesos colombianos", example = "3500000.00")
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;

    @Schema(
            description = "Categoria del producto",
            allowableValues = {"ELECTRONICA", "PAPELERIA", "HOGAR"},
            example = "ELECTRONICA"
    )
    private String categoria;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
