package cliper.apiBoostly.controladores;

import cliper.apiBoostly.daos.Roles;
import cliper.apiBoostly.servicios.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que maneja las solicitudes relacionadas con los roles.
 * Permite realizar operaciones como obtener, crear, actualizar y eliminar roles.
 * @author Sergio Alfonseca
 */
@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final RolesService rolesService;

    @Autowired
    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    /**
     * Obtiene todos los roles.
     * 
     * @return Una lista de todos los roles existentes.
     * @author Sergio Alfonseca
     */
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping
    public List<Roles> getAllRoles() {
        return rolesService.getAllRoles();
    }

    /**
     * Crea un nuevo rol.
     * 
     * @param rol El objeto Roles que contiene los datos del rol a crear.
     * @return ResponseEntity con el rol creado.
     * @author Sergio Alfonseca
     */
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping
    public ResponseEntity<Roles> createRole(@RequestBody Roles rol) {
        Roles newRol = rolesService.createRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRol);
    }

    /**
     * Actualiza un rol existente.
     * 
     * @param id El identificador del rol a actualizar.
     * @param rolDetails Los nuevos detalles del rol.
     * @return ResponseEntity con el rol actualizado.
     * @author Sergio Alfonseca
     */
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/{id}")
    public ResponseEntity<Roles> updateRole(@PathVariable Long id, @RequestBody Roles rolDetails) {
        Roles updatedRol = rolesService.updateRol(id, rolDetails);
        return ResponseEntity.ok(updatedRol);
    }

    /**
     * Elimina un rol específico.
     * 
     * @param id El identificador del rol a eliminar.
     * @return ResponseEntity con el resultado de la operación de eliminación.
     * @author Sergio Alfonseca
     */
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        rolesService.deleteRol(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
