package cliper.apiBoostly.controladores;

import cliper.apiBoostly.daos.Categoria;
import cliper.apiBoostly.dtos.CategoriaDto;
import cliper.apiBoostly.servicios.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDto>> listarCategorias() {
        List<CategoriaDto> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    // Obtener una categoría por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Long id) {
    	Categoria categiriaEncontradaCategoria = categoriaService.obtenerCategoriaPorId(id);
        CategoriaDto categoria = new CategoriaDto(categiriaEncontradaCategoria.getIdCategoria(),categiriaEncontradaCategoria.getNombreCategoria(),categiriaEncontradaCategoria.getDescripcionCategoria());
        if (categoria.getIdCategoria() != null) {
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada");
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaDto categoriaDto) {
        CategoriaDto categoriaCreada = categoriaService.crearCategoria(categoriaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
    }
}
