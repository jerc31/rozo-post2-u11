package com.empresa.catalogo.service;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.entity.Producto;
import com.empresa.catalogo.exception.RecursoNoEncontradoException;
import com.empresa.catalogo.factory.ProductoFactory;
import com.empresa.catalogo.repository.ProductoRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository repo;
    private final ProductoFactory factory;

    public ProductoServiceImpl(ProductoRepository repo, ProductoFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        log.info("Creando producto: nombre={}, categoria={}", dto.getNombre(), dto.getCategoria());
        try {
            Producto producto = factory.toEntity(dto);
            ProductoResponseDTO respuesta = factory.toResponseDTO(repo.save(producto));
            log.info("Producto creado exitosamente con id={}", respuesta.getId());
            return respuesta;
        } catch (RuntimeException ex) {
            log.error("Error al crear producto: nombre={}, categoria={}", dto.getNombre(), dto.getCategoria(), ex);
            throw ex;
        }
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {
        log.debug("Buscando producto con id={}", id);
        Producto producto = repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Producto con id={} no encontrado", id);
                    return new RecursoNoEncontradoException("Producto", id);
                });
        log.info("Producto encontrado con id={}", id);
        return factory.toResponseDTO(producto);
    }

    @Override
    public List<ProductoResponseDTO> listarActivos() {
        log.debug("Listando productos activos");
        List<ProductoResponseDTO> productos = repo.findByActivoTrue().stream()
                .map(factory::toResponseDTO)
                .toList();
        log.info("Productos activos listados: total={}", productos.size());
        return productos;
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando producto con id={}", id);
        buscarPorId(id);
        repo.deleteById(id);
        log.info("Producto con id={} eliminado correctamente", id);
    }
}
