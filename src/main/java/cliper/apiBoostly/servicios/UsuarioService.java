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
    
    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuarios loginUsuario(String mail) {
        Optional<Usuarios> usuario = usuarioRepository.findByMailUsuarioAndGoogleUsuarioFalse(mail);
        
        
        return usuario.orElse(null);
    }
    
    public Usuarios getUsuarioById(Long id) {
        Optional<Usuarios> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null); // Devuelve el usuario si existe, o null si no se encuentra
    }
    
    public Usuarios loginUsuarioGoogle(String mail) {
        Optional<Usuarios> usuario = usuarioRepository.findByGoogleUsuarioTrueAndMailUsuario(mail);
        return usuario.orElse(null);
    }
    
    public Usuarios obtenerUsuarioPorEmail(String email) {
    	
    	Optional<Usuarios> usuario = usuarioRepository.findByMailUsuario(email);
    	
    	if (usuario.isPresent()) {
        	System.out.print("si se encontro");
            return usuario.get();
        }
        System.out.print("pues no");
        return null;
    }
    
    public boolean eliminarUsuarioPorId(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }


    
    public Usuarios obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    
    public Usuarios createUsuario(Usuarios usuario) {
    	
        return usuarioRepository.save(usuario);
    }

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
    
    public Usuarios buscarMail(String mail) {
        Optional<Usuarios> usuario = usuarioRepository.findByMailUsuario(mail);
        
        if (usuario.isPresent()) {
        	System.out.print("si se encontro");
              // Elimina el club encontrado
    
            return usuario.get();
        }
        System.out.print("pues no");
        return null;
    }
    

    @Transactional
    public Usuarios deleteUsuario(String mail, String contrasenya) {
        Optional<Usuarios> usuario = usuarioRepository.findByMailUsuarioAndGoogleUsuarioFalse(mail);
        if (usuario.isPresent()) {
        	usuarioRepository.delete(usuario.get());  // Elimina el club encontrado
            return usuario.get();
        }
        return null;
    }
    
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
