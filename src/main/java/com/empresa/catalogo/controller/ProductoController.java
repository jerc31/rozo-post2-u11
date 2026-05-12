package com.empresa.catalogo.controller;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones CRUD del catalogo")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }
    // anotaciones
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    public ProductoResponseDTO crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return service.crear(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(schema = @Schema(implementation = com.empresa.catalogo.exception.ApiError.class)))
    public ProductoResponseDTO buscar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    @Operation(summary = "Listar productos activos")
    @ApiResponse(responseCode = "200", description = "Listado de productos activos")
    public List<ProductoResponseDTO> listar() {
        return service.listarActivos();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar producto por ID")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(schema = @Schema(implementation = com.empresa.catalogo.exception.ApiError.class)))
    public void eliminar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id) {
        service.eliminar(id);
    }
}
