package cliper.apiBoostly.controladores;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cliper.apiBoostly.daos.Proyectos;
import cliper.apiBoostly.daos.Usuarios;
import cliper.apiBoostly.dtos.ProyectoDto; // Opcional: DTO para recibir datos del proyecto
import cliper.apiBoostly.servicios.ProyectoService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    /**
     * Crea un nuevo proyecto. Se asigna automáticamente el usuario logueado y la fecha de inicio se establece en el momento de la creación.
     */
    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody ProyectoDto proyectoDto) {
        
        // Asignar el usuario logueado y la fecha de inicio (ahora) al proyecto
        proyectoDto.setFechaInicioProyecto(LocalDateTime.now());

        // Aquí se pueden agregar validaciones adicionales, como que fecha_finalizacion sea posterior a fecha_inicio, etc.
        Proyectos proyectoCreado = proyectoService.crearProyecto(proyectoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoCreado);
    }

    /**
     * Lista todos los proyectos.
     */
    @GetMapping
    public ResponseEntity<List<Proyectos>> listarProyectos() {
        List<Proyectos> proyectos = proyectoService.listarProyectos();
        return ResponseEntity.ok(proyectos);
    }

    /**
     * Obtiene los detalles de un proyecto por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProyecto(@PathVariable Long id) {
        Proyectos proyecto = proyectoService.obtenerProyectoPorId(id);
        if (proyecto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }
        return ResponseEntity.ok(proyecto);
    }

    /**
     * Actualiza un proyecto. Solo el creador del proyecto puede editarlo.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProyecto(@PathVariable Long id, @RequestBody ProyectoDto proyectoDto, HttpSession session) {
        Usuarios usuarioLogueado = (Usuarios) session.getAttribute("usuario");
        if (usuarioLogueado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
        Proyectos proyectoExistente = proyectoService.obtenerProyectoPorId(id);
        if (proyectoExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }
        // Validar que el usuario logueado sea el creador del proyecto
        if (!proyectoExistente.getUsuario().getId().equals(usuarioLogueado.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar este proyecto");
        }
        // Actualizar el proyecto con los nuevos datos (la lógica se implementa en el servicio)
        Proyectos proyectoActualizado = proyectoService.actualizarProyecto(id, proyectoDto);
        return ResponseEntity.ok(proyectoActualizado);
    }

    /**
     * Elimina un proyecto. Solo el creador del proyecto puede eliminarlo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProyecto(@PathVariable Long id, HttpSession session) {
        Usuarios usuarioLogueado = (Usuarios) session.getAttribute("usuario");
        if (usuarioLogueado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
        Proyectos proyectoExistente = proyectoService.obtenerProyectoPorId(id);
        if (proyectoExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }
        // Validar que el usuario logueado sea el creador del proyecto
        if (!proyectoExistente.getUsuario().getId().equals(usuarioLogueado.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar este proyecto");
        }
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.ok("Proyecto eliminado");
    }
}

