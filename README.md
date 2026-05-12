# Programacion Web - Unidad 11

## Post-Contenido 2 - Logging con SLF4J/Logback y Documentacion con Swagger/OpenAPI

## Autor

- Nombre: Kevin Javier Ramirez
- Programa: Ingenieria de Sistemas
- Asignatura: Programacion Web
- Unidad: 11 - Buenas Practicas y Patrones de Diseno
- Actividad: Post-Contenido 2
- Fecha: 12/05/2026

## Objetivo

Este proyecto toma como base el catalogo de productos del Post-Contenido 1 y
agrega logging profesional con SLF4J/Logback y documentacion interactiva con
Swagger/OpenAPI usando `springdoc-openapi`.

La API mantiene la arquitectura por capas con SOLID, DAO, DTO, Factory y manejo
centralizado de excepciones mediante `@RestControllerAdvice`.

## Tecnologias

- Java 17
- Spring Boot 3.2.5
- Maven
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation
- SLF4J
- Logback
- springdoc-openapi 2.3.0

# Arquitectura del Proyecto

```text
src/main/java/com/empresa/catalogo/
|-- controller/
|   `-- ProductoController.java
|
|-- service/
|   |-- ProductoService.java
|   `-- ProductoServiceImpl.java
|
|-- repository/
|   `-- ProductoRepository.java
|
|-- dto/
|   |-- ProductoRequestDTO.java
|   `-- ProductoResponseDTO.java
|
|-- entity/
|   `-- Producto.java
|
|-- factory/
|   `-- ProductoFactory.java
|
|-- exception/
|   |-- ApiError.java
|   |-- GlobalExceptionHandler.java
|   `-- RecursoNoEncontradoException.java
|
`-- CatalogoApplication.java

src/main/resources/
|-- application.properties
`-- logback-spring.xml
```

---

## Arquitectura en Capas

```text
Cliente HTTP
    |
    v
ProductoController
    |
    v
ProductoService (interfaz - DIP)
    |
    v
ProductoServiceImpl
    |                    \
    v                     v
ProductoRepository      ProductoFactory
    |                     |
    v                     v
Producto Entity      DTOs Request/Response

GlobalExceptionHandler maneja errores globales
SLF4J + Logback registran eventos del sistema
Swagger/OpenAPI documenta la API REST
```

---

## Logging implementado

`ProductoServiceImpl` usa un logger estatico:

```java
private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);
```

Los mensajes usan placeholders `{}` y niveles adecuados:

- `INFO`: creacion, busqueda exitosa, listado y eliminacion.
- `DEBUG`: busquedas y listados internos.
- `WARN`: producto inexistente.
- `ERROR`: excepciones al crear producto.

## Configuracion de Logback

El archivo `src/main/resources/logback-spring.xml` define:

- Appender `CONSOLA` con formato corto para terminal.
- Appender `ARCHIVO` con `RollingFileAppender`.
- Archivo activo: `logs/catalogo.log`.
- Rotacion diaria: `logs/catalogo.yyyy-MM-dd.log`.
- Historial: 30 dias.
- Nivel global: `INFO`.
- Nivel del paquete `com.empresa.catalogo`: `DEBUG`.

La carpeta `logs/` esta en `.gitignore`, porque contiene archivos generados en
tiempo de ejecucion.

## Swagger/OpenAPI

La documentacion se configura con:

- `@OpenAPIDefinition` en `CatalogoApplication`.
- `@Tag`, `@Operation`, `@ApiResponse` y `@Parameter` en `ProductoController`.
- `@Schema` en `ProductoRequestDTO`, `ProductoResponseDTO` y `ApiError`.

URL de Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

JSON OpenAPI:

```text
http://localhost:8080/api-docs
```

## Ejecucion

Desde PowerShell, en la raiz del proyecto:

```powershell
cd "C:\Users\KEVIN\Downloads\programaciónWeb\catalogo"
.\mvnw.cmd compile
.\mvnw.cmd spring-boot:run
```

La API queda disponible en:

```text
http://localhost:8080/api/productos
```

## Endpoints

| Metodo | Ruta | Respuestas documentadas | Descripcion |
| --- | --- | --- | --- |
| GET | `/api/productos` | 200 | Lista productos activos |
| GET | `/api/productos/{id}` | 200, 404 | Busca un producto por id |
| POST | `/api/productos` | 201, 400 | Crea un producto |
| DELETE | `/api/productos/{id}` | 204, 404 | Elimina un producto por id |

## Pruebas con curl

### Crear producto y generar logs

```powershell
curl.exe -i -X POST http://localhost:8080/api/productos `
  -H "Content-Type: application/json" `
  -d "{\"nombre\":\"Laptop\",\"precio\":3500000,\"categoria\":\"ELECTRONICA\"}"
```

### Listar productos y generar logs

```powershell
curl.exe -i http://localhost:8080/api/productos
```

### Buscar producto inexistente

```powershell
curl.exe -i http://localhost:8080/api/productos/999
```

### Validar body vacio

```powershell
curl.exe -i -X POST http://localhost:8080/api/productos `
  -H "Content-Type: application/json" `
  -d "{}"
```

## Checkpoints y evidencias

### Checkpoint 1 - Logs SLF4J en consola

1. Ejecutar:

```powershell
.\mvnw.cmd spring-boot:run
```

2. En otra terminal crear y listar productos:

```powershell
curl.exe -i -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d "{\"nombre\":\"Laptop\",\"precio\":3500000,\"categoria\":\"ELECTRONICA\"}"
curl.exe -i http://localhost:8080/api/productos
```

3. Tomar captura de la consola donde se vean mensajes como:

```text
INFO  c.e.c.service.ProductoServiceImpl - Creando producto: nombre=Laptop, categoria=ELECTRONICA
INFO  c.e.c.service.ProductoServiceImpl - Producto creado exitosamente con id=1
```

### Checkpoint 2 - Archivo logs/catalogo.log

1. Con la aplicacion encendida, ejecutar operaciones `POST`, `GET` y `GET /999`.
2. Abrir el archivo:

```powershell
Get-Content logs\catalogo.log
```

3. Tomar captura del terminal o del editor mostrando registros con fecha completa.

### Checkpoint 3 - Swagger UI

1. Abrir en el navegador:

```text
http://localhost:8080/swagger-ui.html
```

2. Expandir el grupo `Productos`.
3. Expandir al menos un endpoint, por ejemplo `POST /api/productos`.
4. Verificar que aparecen descripcion, request body y respuestas `201` y `400`.
5. Revisar tambien `GET /api/productos/{id}` para confirmar `200` y `404`.

---

## Capturas del Proyecto

Las capturas se encuentran en la carpeta `evidencias/`.

### Mensajes app corriendo

![mensajes_corriendo](evidencias/mensajes_app_corrriendo.png)

### Mensajes archivo logs

![mensajes_logs](evidencias/mensajes_archivo_logs.png)

### Swagger UI 

![swaggerUi](evidencias/SwaggerUI.png)