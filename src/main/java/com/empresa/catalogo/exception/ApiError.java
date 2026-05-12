package com.empresa.catalogo.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class ApiError {
    @Schema(description = "Codigo HTTP de la respuesta", example = "404")
    private final int status;

    @Schema(description = "Nombre del error HTTP", example = "Not Found")
    private final String error;

    @Schema(description = "Mensaje legible del error", example = "Producto con id 999 no encontrado.")
    private final String mensaje;

    @Schema(description = "Fecha y hora del error")
    private final String timestamp;

    @Schema(description = "Ruta donde ocurrio el error", example = "/api/productos/999")
    private final String path;

    public ApiError(int status, String error, String mensaje, String path) {
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now().toString();
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
