package cliper.apiBoostly.controladores;

import cliper.apiBoostly.daos.Roles;
import cliper.apiBoostly.servicios.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final RolesService rolesService;

    @Autowired
    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    // Obtener todos los roles
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping
    public List<Roles> getAllRoles() {
        return rolesService.getAllRoles();
    }

    // Crear un nuevo rol
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping
    public ResponseEntity<Roles> createRole(@RequestBody Roles rol) {
        Roles newRol = rolesService.createRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRol);
    }

    // Actualizar un rol existente
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/{id}")
    public ResponseEntity<Roles> updateRole(@PathVariable Long id, @RequestBody Roles rolDetails) {
        Roles updatedRol = rolesService.updateRol(id, rolDetails);
        return ResponseEntity.ok(updatedRol);
    }

    // Eliminar un rol
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        rolesService.deleteRol(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
