package cliper.apiBoostly.controladores;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cliper.apiBoostly.daos.Roles;
import cliper.apiBoostly.daos.Usuarios;
import cliper.apiBoostly.dtos.TokenContraseñaDto;
import cliper.apiBoostly.dtos.UsuariosDto;
import cliper.apiBoostly.servicios.RolesService;
import cliper.apiBoostly.servicios.UsuarioService;
import cliper.apiBoostly.servicios.UsuarioToken;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolesService rolesService;
    private final UsuarioToken usuarioToken;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, RolesService rolesService, UsuarioToken usuarioToken) {
        this.usuarioService = usuarioService;
        this.rolesService = rolesService;
        this.usuarioToken = usuarioToken;
    }

    /**
     * Obtiene todos los usuarios registrados.
     * 
     * @return Una lista de todos los usuarios.
     * @author Sergio Alfonseca
     */
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping
    public List<Usuarios> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }
    
    /**
     * Obtiene un usuario específico por su ID.
     * 
     * @param id El identificador del usuario a obtener.
     * @return ResponseEntity con el usuario encontrado o un error si no se encuentra.
     * @author Sergio Alfonseca
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosDto> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuarios usuario = usuarioService.obtenerUsuarioPorId(id);
        UsuariosDto usuarioEncontrado = convertirAUsuarioDto(usuario);
        if (usuario != null) {
            return ResponseEntity.ok(usuarioEncontrado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Método privado para convertir un usuario a UsuarioDto.
     * 
     * @param usuario El objeto Usuario a convertir.
     * @return El objeto UsuarioDto correspondiente.
     * @author Sergio Alfonseca
     */
    private UsuariosDto convertirAUsuarioDto(Usuarios usuario) {
        UsuariosDto usuarioDto = new UsuariosDto();
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDto.setApellidosUsuario(usuario.getApellidosUsuario());
        usuarioDto.setMailUsuario(usuario.getMailUsuario());
        usuarioDto.setFechaNacimientoUsuario(usuario.getFechaNacimientoUsuario());
        usuarioDto.setNicknameUsuario(usuario.getNicknameUsuario());
        usuarioDto.setContrasenyaUsuario(usuario.getContrasenyaUsuario());
        usuarioDto.setFechaAltaUsuario(usuario.getFechaAltaUsuario());
        usuarioDto.setDescripcionUsuario(usuario.getDescripcionUsuario());
        usuarioDto.setDniUsuario(usuario.getDniUsuario());
        usuarioDto.setTelefonoUsuario(usuario.getTelefonoUsuario());
        usuarioDto.setImgUsuario(usuario.getImgUsuario()); // Recibe la imagen como byte[]

        // Asignar rol (asumiendo que tienes un campo rol en UsuarioDto)
        if (usuario.getRol() != null) {
            usuarioDto.setRol(usuario.getRol().getIdRol()); // Asumimos que se quiere el ID del rol
        }

        usuarioDto.setGoogleUsuario(usuario.getGoogleUsuario());
        usuarioDto.setTokenRecuperacion(usuario.getTokenRecuperacion());
        usuarioDto.setTokenExpiracion(usuario.getTokenExpiracion());

        return usuarioDto;
    }

    /**
     * Crea un nuevo usuario.
     * 
     * @param usuarioDto El objeto que contiene los datos del usuario a crear.
     * @return ResponseEntity con el usuario creado.
     * @author Sergio Alfonseca
     */
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody UsuariosDto usuarioDto) {
        // Buscar el rol por idRol
        Roles rol = rolesService.buscarRol(usuarioDto.getRol());
        if (rol != null) {
            // Crear el usuario a partir del DTO y el rol encontrado
            Usuarios nuevoUsuario = new Usuarios();
            nuevoUsuario.setId(usuarioDto.getId());
            nuevoUsuario.setNombreUsuario(usuarioDto.getNombreUsuario());
            nuevoUsuario.setApellidosUsuario(usuarioDto.getApellidosUsuario());
            nuevoUsuario.setMailUsuario(usuarioDto.getMailUsuario());
            nuevoUsuario.setFechaNacimientoUsuario(usuarioDto.getFechaNacimientoUsuario());
            nuevoUsuario.setNicknameUsuario(usuarioDto.getNicknameUsuario());
            nuevoUsuario.setContrasenyaUsuario(usuarioDto.getContrasenyaUsuario());
            nuevoUsuario.setFechaAltaUsuario(usuarioDto.getFechaAltaUsuario());
            nuevoUsuario.setDescripcionUsuario(usuarioDto.getDescripcionUsuario());
            nuevoUsuario.setDniUsuario(usuarioDto.getDniUsuario());
            nuevoUsuario.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());

            // La imagen ya se recibe directamente como byte[]
            if (usuarioDto.getImgUsuario() != null) {
                byte[] imgBytes = usuarioDto.getImgUsuario(); // Recibimos la imagen como byte[]
                nuevoUsuario.setImgUsuario(imgBytes); // Establecer los datos binarios de la imagen
            }

            nuevoUsuario.setGoogleUsuario(usuarioDto.getGoogleUsuario());
            nuevoUsuario.setTokenRecuperacion(usuarioDto.getTokenRecuperacion());

            // Convertir Date a Timestamp si es necesario para 'tokenExpiracion'
            if (usuarioDto.getTokenExpiracion() != null) {
                Timestamp tokenExpiracionTimestamp = new Timestamp(usuarioDto.getTokenExpiracion().getTime());
                nuevoUsuario.setTokenExpiracion(tokenExpiracionTimestamp); // Asignar el Timestamp
            }

            nuevoUsuario.setRol(rol); // Asignar el rol al usuario

            // Verificar si el correo ya existe
            if (usuarioService.buscarMail(nuevoUsuario.getMailUsuario()) == null) {
            	UsuariosDto usuarioNuevoDto =  convertirAUsuarioDto(usuarioService.createUsuario(nuevoUsuario));
                
                return ResponseEntity.ok(usuarioNuevoDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rol no encontrado");
        }
    }
    
    /**
     * Elimina un usuario específico.
     * 
     * @param id El identificador del usuario a eliminar.
     * @return ResponseEntity con el resultado de la operación de eliminación.
     * @author Sergio Alfonseca
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = usuarioService.eliminarUsuarioPorId(id);
        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    /**
     * Obtiene un usuario específico por su correo electrónico.
     * 
     * @param email El correo electrónico del usuario a obtener.
     * @return ResponseEntity con el usuario encontrado o un error si no se encuentra.
     * @author Sergio Alfonseca
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuariosDto> obtenerUsuarioPorEmail(@PathVariable String email) {
        Usuarios usuario = usuarioService.obtenerUsuarioPorEmail(email); // Llamada al servicio
        if (usuario != null) {
            return ResponseEntity.ok(convertirAUsuarioDto(usuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Genera un token para recuperar la contraseña de un usuario.
     * 
     * @param request El objeto que contiene la información necesaria para generar el token.
     * @return ResponseEntity indicando el éxito de la operación.
     * @author Sergio Alfonseca
     */
    @PostMapping("/generar-token")
    public ResponseEntity<String> generarToken(@RequestBody TokenContraseñaDto request) {
        usuarioToken.generarToken(request.getEmail(), request.getTokenRecuperacion());
        return ResponseEntity.ok("Token generado y enviado correctamente");
    }

    /**
     * Resetea la contraseña de un usuario utilizando un token de recuperación.
     * 
     * @param request El objeto que contiene el token y la nueva contraseña.
     * @return ResponseEntity indicando si la contraseña fue actualizada correctamente o si el token es inválido.
     * @author Sergio Alfonseca
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody TokenContraseñaDto request) {
        boolean actualizado = usuarioToken.actualizarContrasena(request.getTokenRecuperacion(),
                request.getNuevaContrasena());
        return actualizado ? ResponseEntity.ok("Contraseña actualizada correctamente")
                : ResponseEntity.badRequest().body("Token inválido o expirado");
    }
}
