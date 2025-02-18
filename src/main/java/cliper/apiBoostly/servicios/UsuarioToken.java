package cliper.apiBoostly.servicios;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cliper.apiBoostly.daos.Usuarios;
import cliper.apiBoostly.repository.UsuarioRepository;

@Service
public class UsuarioToken {

	
	private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioToken(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void generarToken(String email, String token) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByMailUsuario(email);
        usuarioOpt.ifPresent(usuario -> {
            usuario.setTokenRecuperacion(token);
            usuario.setTokenExpiracion(Timestamp.from(Instant.now().plusSeconds(300))); // Token válido por 5 minutos
            usuarioRepository.save(usuario);
        });
    }

    public boolean actualizarContrasena(String token, String nuevaContrasena) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByTokenRecuperacion(token);
        if (usuarioOpt.isPresent()) {
        	Usuarios usuario = usuarioOpt.get();
            if (usuario.getTokenExpiracion() != null && usuario.getTokenExpiracion().before(Timestamp.from(Instant.now()))) {
                return false; // Token expirado
            }
            usuario.setContrasenyaUsuario(nuevaContrasena); // Se recomienda encriptar la contraseña antes de guardarla
            usuario.setTokenRecuperacion(null); // Eliminar el token tras el uso
            usuario.setTokenExpiracion(null);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
	
}
