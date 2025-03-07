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

    /**
     * Obtiene todos los roles.
     * 
     * @return Una lista de todos los roles existentes.
     */
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    /**
     * Busca un rol por su ID.
     * 
     * @param idRol El ID del rol a buscar.
     * @return El rol encontrado o null si no existe.
     */
    public Roles buscarRol(Long idRol) {
        Optional<Roles> rol = rolesRepository.findById(idRol);
        return rol.orElse(null); // Retorna el rol si existe, o null si no se encuentra
    }

    /**
     * Crea un nuevo rol.
     * 
     * @param rol El rol a crear.
     * @return El rol creado.
     */
    public Roles createRol(Roles rol) {
        return rolesRepository.save(rol);
    }

    /**
     * Actualiza un rol existente.
     * 
     * @param idRol El ID del rol a actualizar.
     * @param rolDetails Los nuevos detalles del rol.
     * @return El rol actualizado.
     */
    public Roles updateRol(Long idRol, Roles rolDetails) {
        Roles rol = rolesRepository.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setNombreRol(rolDetails.getNombreRol());
        rol.setDescripcionRol(rolDetails.getDescripcionRol());
        return rolesRepository.save(rol);
    }

    /**
     * Elimina un rol por su ID.
     * 
     * @param idRol El ID del rol a eliminar.
     */
    public void deleteRol(Long idRol) {
        Roles rol = rolesRepository.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rolesRepository.delete(rol);
    }
}
