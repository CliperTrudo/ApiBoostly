package cliper.apiBoostly.controladores;

import cliper.apiBoostly.daos.Categoria;
import cliper.apiBoostly.daos.Proyectos;
import cliper.apiBoostly.dtos.ProyectoDto;
import cliper.apiBoostly.dtos.CategoriaDto;
import cliper.apiBoostly.servicios.ProyectoService;
import cliper.apiBoostly.servicios.CategoriaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador que maneja las solicitudes relacionadas con los proyectos.
 * Permite realizar operaciones como crear, obtener, actualizar y eliminar proyectos.
 * @author Sergio Alfonseca
 */
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Crea un nuevo proyecto. Se asigna automáticamente el usuario logueado y la
     * fecha de inicio se establece en el momento de la creación.
     * 
     * @param proyectoDto Objeto que contiene los datos del proyecto a crear.
     * @return ResponseEntity con el proyecto creado.
     * @author Sergio Alfonseca
     */
    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody ProyectoDto proyectoDto) {
        // Asignar el usuario logueado y la fecha de inicio
        System.out.println(proyectoDto.toString());
        proyectoDto.setFechaInicioProyecto(LocalDateTime.now());

        Categoria categiriaEncontradaCategoria = categoriaService.obtenerCategoriaPorId(proyectoDto.getIdCategoria());
        CategoriaDto categoria = new CategoriaDto(categiriaEncontradaCategoria.getIdCategoria(),
                categiriaEncontradaCategoria.getNombreCategoria(),
                categiriaEncontradaCategoria.getDescripcionCategoria());

        if (categoria.getIdCategoria() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoría no válida");
        }

        // Llamar al servicio para crear el proyecto
        Proyectos proyectoCreado = proyectoService.crearProyecto(proyectoDto);

        // Convertir la entidad Proyectos a ProyectoDto antes de devolverla
        ProyectoDto proyectoDtoResponse = convertirAProyectoDto(proyectoCreado);
        return ResponseEntity.ok(proyectoDtoResponse);
    }

    /**
     * Obtiene los proyectos de un usuario específico.
     * 
     * @param idUsuario El identificador del usuario cuyos proyectos se desean obtener.
     * @return ResponseEntity con la lista de proyectos del usuario.
     * @author Sergio Alfonseca
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ProyectoDto>> obtenerProyectosPorUsuario(@PathVariable Long idUsuario) {
        List<Proyectos> proyectos = proyectoService.obtenerProyectosPorUsuario(idUsuario);
        List<ProyectoDto> proyectosDto = proyectos.stream().map(this::convertirAProyectoDto)
                .collect(Collectors.toList());
        if (!proyectos.isEmpty()) {
            return ResponseEntity.ok(proyectosDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene un proyecto específico por su ID.
     * 
     * @param id El identificador del proyecto a obtener.
     * @return ResponseEntity con el proyecto encontrado o un error si no se encuentra.
     * @author Sergio Alfonseca
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDto> obtenerProyectoPorId(@PathVariable Long id) {
        System.out.println("hola");
        Proyectos proyecto = proyectoService.obtenerProyectoPorId(id);
        ProyectoDto proyectoDtoResponse = convertirAProyectoDto(proyecto);
        if (proyecto != null) {
            return ResponseEntity.ok(proyectoDtoResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los proyectos.
     * 
     * @return ResponseEntity con la lista de todos los proyectos.
     * @author Sergio Alfonseca
     */
    @GetMapping
    public ResponseEntity<List<ProyectoDto>> obtenerProyectos() {
        List<Proyectos> proyectos = proyectoService.listarProyectos();
        List<ProyectoDto> proyectosDto = proyectos.stream().map(this::convertirAProyectoDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proyectosDto);
    }

    /**
     * Obtiene los proyectos de una categoría específica.
     * 
     * @param idCategoria El identificador de la categoría cuyos proyectos se desean obtener.
     * @return ResponseEntity con la lista de proyectos de la categoría.
     * @author Sergio Alfonseca
     */
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProyectoDto>> obtenerProyectosPorCategoria(@PathVariable Long idCategoria) {
        List<Proyectos> proyectos = proyectoService.obtenerProyectosPorCategoria(idCategoria);
        List<ProyectoDto> proyectosDto = proyectos.stream().map(this::convertirAProyectoDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proyectosDto);
    }

    /**
     * Actualiza un proyecto específico.
     * 
     * @param id El identificador del proyecto a actualizar.
     * @param proyectoDto Los nuevos datos del proyecto a actualizar.
     * @return ResponseEntity con el proyecto actualizado o un error si no se encuentra.
     * @author Sergio Alfonseca
     */
    @PutMapping("/{id}")
    public ResponseEntity<Proyectos> actualizarProyecto(@PathVariable Long id, @RequestBody ProyectoDto proyectoDto) {
        Proyectos proyectoActualizado = proyectoService.actualizarProyecto(id, proyectoDto);
        return (proyectoActualizado != null) ? ResponseEntity.ok(proyectoActualizado)
                : ResponseEntity.notFound().build();
    }

    /**
     * Elimina un proyecto específico.
     * 
     * @param id El identificador del proyecto a eliminar.
     * @return ResponseEntity con el resultado de la operación de eliminación.
     * @author Sergio Alfonseca
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminarProyecto(@PathVariable Long id) {
        boolean respuesta = proyectoService.eliminarProyecto(id);
        return (respuesta != false) ? ResponseEntity.ok(respuesta) : ResponseEntity.notFound().build();
    }

    /**
     * Método privado para convertir la entidad Proyectos a ProyectoDto.
     * 
     * @param proyecto La entidad Proyectos a convertir.
     * @return El objeto ProyectoDto correspondiente.
     * @author Sergio Alfonseca
     */
    private ProyectoDto convertirAProyectoDto(Proyectos proyecto) {
        ProyectoDto proyectoDto = new ProyectoDto();
        proyectoDto.setIdProyecto(proyecto.getIdProyecto());
        proyectoDto.setIdUsuario(proyecto.getUsuario().getId());
        proyectoDto.setNombreProyecto(proyecto.getNombreProyecto());
        proyectoDto.setDescripcionProyecto(proyecto.getDescripcionProyecto());
        proyectoDto.setImagen1Proyecto(proyecto.getImagen1Proyecto());
        proyectoDto.setImagen2Proyecto(proyecto.getImagen2Proyecto());
        proyectoDto.setImagen3Proyecto(proyecto.getImagen3Proyecto());
        proyectoDto.setFechaInicioProyecto(proyecto.getFechaInicioProyecto());
        proyectoDto.setFechaFinalizacionProyecto(proyecto.getFechaFinalizacionProyecto());
        proyectoDto.setMetaRecaudacionProyecto(proyecto.getMetaRecaudacionProyecto());
        proyectoDto.setIdEstado(proyecto.getEstadoProyecto().getId());
        proyectoDto.setIdCategoria(proyecto.getCategoriaProyecto().getIdCategoria()); // Ahora se usa la id de la categoría
        return proyectoDto;
    }
}
