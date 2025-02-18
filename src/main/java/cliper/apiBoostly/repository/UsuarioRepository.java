package cliper.apiBoostly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cliper.apiBoostly.daos.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

	Optional<Usuarios> findByMailUsuarioAndGoogleUsuarioFalse(String mailUsuario);

	Optional<Usuarios> findByMailUsuario(String mailUsuario);

	Optional<Usuarios> findByGoogleUsuarioTrueAndMailUsuario(String mailUsuario);

	Optional<Usuarios> findByTokenRecuperacion(String token);

}
