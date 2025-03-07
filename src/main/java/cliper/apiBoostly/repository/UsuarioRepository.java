package cliper.apiBoostly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cliper.apiBoostly.daos.Usuarios;

/**
 * Repositorio para la entidad Usuarios.
 * Esta interfaz extiende JpaRepository y proporciona operaciones CRUD sobre la entidad Usuarios.
 * Además, permite realizar consultas personalizadas para los usuarios.
 * @author Sergio Alfonseca
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    /**
     * Consulta personalizada para encontrar un usuario por correo y verificar si no tiene cuenta de Google.
     * 
     * @param mailUsuario El correo electrónico del usuario.
     * @return Un Optional con el usuario encontrado, si existe.
     */
    Optional<Usuarios> findByMailUsuarioAndGoogleUsuarioFalse(String mailUsuario);

    /**
     * Consulta personalizada para encontrar un usuario por su correo electrónico.
     * 
     * @param mailUsuario El correo electrónico del usuario.
     * @return Un Optional con el usuario encontrado, si existe.
     */
    Optional<Usuarios> findByMailUsuario(String mailUsuario);

    /**
     * Consulta personalizada para encontrar un usuario con cuenta de Google, dado su correo electrónico.
     * 
     * @param mailUsuario El correo electrónico del usuario.
     * @return Un Optional con el usuario encontrado, si existe.
     */
    Optional<Usuarios> findByGoogleUsuarioTrueAndMailUsuario(String mailUsuario);

    /**
     * Consulta personalizada para encontrar un usuario por su token de recuperación.
     * 
     * @param token El token de recuperación.
     * @return Un Optional con el usuario encontrado, si existe.
     */
    Optional<Usuarios> findByTokenRecuperacion(String token);
}
