package cliper.apiBoostly.controladores;

import cliper.apiBoostly.daos.Categoria;
import cliper.apiBoostly.dtos.CategoriaDto;
import cliper.apiBoostly.servicios.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que maneja las solicitudes relacionadas con las categorías.
 * Permite realizar operaciones como listar, obtener y crear categorías.
 * @author Sergio Alfonseca
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Método encargado de listar todas las categorías.
     * 
     * @return ResponseEntity con una lista de categorías.
     * @author Sergio Alfonseca
     */
    @GetMapping
    public ResponseEntity<List<CategoriaDto>> listarCategorias() {
        List<CategoriaDto> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Método encargado de obtener una categoría por su ID.
     * 
     * @param id El identificador único de la categoría.
     * @return ResponseEntity con la categoría encontrada o un error si no se encuentra.
     * @author Sergio Alfonseca
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Long id) {
        Categoria categiriaEncontradaCategoria = categoriaService.obtenerCategoriaPorId(id);
        CategoriaDto categoria = new CategoriaDto(categiriaEncontradaCategoria.getIdCategoria(),categiriaEncontradaCategoria.getNombreCategoria(),categiriaEncontradaCategoria.getDescripcionCategoria());
        if (categoria.getIdCategoria() != null) {
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada");
    }

    /**
     * Método encargado de crear una nueva categoría.
     * 
     * @param categoriaDto Objeto que contiene los datos de la nueva categoría.
     * @return ResponseEntity con la categoría creada.
     * @author Sergio Alfonseca
     */
    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaDto categoriaDto) {
        CategoriaDto categoriaCreada = categoriaService.crearCategoria(categoriaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
    }
}
