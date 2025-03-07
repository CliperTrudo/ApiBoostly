package cliper.apiBoostly.servicios;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cliper.apiBoostly.daos.Usuarios;
import cliper.apiBoostly.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    /**
     * Obtiene todos los usuarios registrados.
     * 
     * @return Una lista de todos los usuarios.
     */
    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Realiza login de un usuario por su correo electrónico, asegurándose de que no tenga cuenta de Google.
     * 
     * @param mail El correo electrónico del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuarios loginUsuario(String mail) {
        Optional<Usuarios> usuario = usuarioRepository.findByMailUsuarioAndGoogleUsuarioFalse(mail);
        return usuario.orElse(null);
    }
    
    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id El ID del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuarios getUsuarioById(Long id) {
        Optional<Usuarios> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null); // Devuelve el usuario si existe, o null si no se encuentra
    }
    
    /**
     * Realiza login de un usuario que tiene cuenta de Google por su correo electrónico.
     * 
     * @param mail El correo electrónico del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuarios loginUsuarioGoogle(String mail) {
        Optional<Usuarios> usuario = usuarioRepository.findByGoogleUsuarioTrueAndMailUsuario(mail);
        return usuario.orElse(null);
    }
    
    /**
     * Obtiene un usuario por su correo electrónico.
     * 
     * @param email El correo electrónico del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuarios obtenerUsuarioPorEmail(String email) {
        Optional<Usuarios> usuario = usuarioRepository.findByMailUsuario(email);
        return usuario.orElse(null);
    }
    
    /**
     * Elimina un usuario por su ID.
     * 
     * @param id El ID del usuario a eliminar.
     * @return true si el usuario fue eliminado, false si no existía.
     */
    public boolean eliminarUsuarioPorId(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id El ID del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuarios obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Crea un nuevo usuario.
     * 
     * @param usuario El usuario a crear.
     * @return El usuario creado.
     */
    public Usuarios createUsuario(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param id El ID del usuario a actualizar.
     * @param usuarioDetails Los nuevos detalles del usuario.
     * @return El usuario actualizado.
     */
    public Usuarios updateUsuario(Long id, Usuarios usuarioDetails) {
        Usuarios usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombreUsuario(usuarioDetails.getNombreUsuario());
        usuario.setApellidosUsuario(usuarioDetails.getApellidosUsuario());
        usuario.setMailUsuario(usuarioDetails.getMailUsuario());
        usuario.setFechaNacimientoUsuario(usuarioDetails.getFechaNacimientoUsuario());
        usuario.setNicknameUsuario(usuarioDetails.getNicknameUsuario());
        usuario.setContrasenyaUsuario(usuarioDetails.getContrasenyaUsuario());
        usuario.setFechaAltaUsuario(usuarioDetails.getFechaAltaUsuario());
        usuario.setDescripcionUsuario(usuarioDetails.getDescripcionUsuario());
        usuario.setDniUsuario(usuarioDetails.getDniUsuario());
        usuario.setTelefonoUsuario(usuarioDetails.getTelefonoUsuario());
        usuario.setImgUsuario(usuarioDetails.getImgUsuario());
        usuario.setRol(usuarioDetails.getRol());
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param mail El correo electrónico del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuarios buscarMail(String mail) {
        Optional<Usuarios> usuario = usuarioRepository.findByMailUsuario(mail);
        return usuario.orElse(null);
    }

    /**
     * Elimina un usuario por correo electrónico y contraseña.
     * 
     * @param mail El correo electrónico del usuario.
     * @param contrasenya La contraseña del usuario.
     * @return El usuario eliminado o null si no se encuentra.
     */
    @Transactional
    public Usuarios deleteUsuario(String mail, String contrasenya) {
        Optional<Usuarios> usuario = usuarioRepository.findByMailUsuarioAndGoogleUsuarioFalse(mail);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return usuario.get();
        }
        return null;
    }
    
    /**
     * Guarda el token de recuperación para un usuario.
     * 
     * @param mail El correo electrónico del usuario.
     * @param token El token de recuperación.
     */
    @Transactional
    public void guardarTokenRecuperacion(String mail, String token) {
        Optional<Usuarios> usuarioOptional = usuarioRepository.findByMailUsuario(mail);
        
        if (usuarioOptional.isPresent()) {
            Usuarios usuario = usuarioOptional.get();
            usuario.setTokenRecuperacion(token);
            usuario.setTokenExpiracion(new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 minutos después
            usuarioRepository.save(usuario);
        }
    }
    
    /**
     * Restablece la contraseña de un usuario si el token de recuperación es válido.
     * 
     * @param token El token de recuperación.
     * @param nuevaContrasena La nueva contraseña.
     * @return true si la contraseña se restableció correctamente, false si el token ha expirado.
     */
    @Transactional
    public boolean restablecerContrasena(String token, String nuevaContrasena) {
        Optional<Usuarios> usuarioOptional = usuarioRepository.findByTokenRecuperacion(token);
        
        if (usuarioOptional.isPresent()) {
            Usuarios usuario = usuarioOptional.get();
            
            // Validar si el token ha expirado
            if (usuario.getTokenExpiracion().before(new Timestamp(System.currentTimeMillis()))) {
                return false; // Token expirado
            }
            
            // Cambiar contraseña y eliminar token
            usuario.setContrasenyaUsuario(nuevaContrasena);
            usuario.setTokenRecuperacion(null);
            usuario.setTokenExpiracion(null);
            usuarioRepository.save(usuario);
            return true;
        }
        
        return false;
    }
    
}
