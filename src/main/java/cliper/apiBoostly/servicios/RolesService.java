package cliper.apiBoostly.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cliper.apiBoostly.daos.Roles;
import cliper.apiBoostly.repository.RolesRepository;

@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    @Autowired
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    // Obtener todos los roles
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    // Buscar un rol por su id
    public Roles buscarRol(Long idRol) {
        Optional<Roles> rol = rolesRepository.findById(idRol);
        return rol.orElse(null); // Retorna el rol si existe, o null si no se encuentra
    }

    // Crear un nuevo rol
    public Roles createRol(Roles rol) {
        return rolesRepository.save(rol);
    }

    // Actualizar un rol existente
    public Roles updateRol(Long idRol, Roles rolDetails) {
        Roles rol = rolesRepository.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setNombreRol(rolDetails.getNombreRol());
        rol.setDescripcionRol(rolDetails.getDescripcionRol());
        return rolesRepository.save(rol);
    }

    // Eliminar un rol por su id
    public void deleteRol(Long idRol) {
        Roles rol = rolesRepository.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rolesRepository.delete(rol);
    }
}
