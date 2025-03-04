package cliper.apiBoostly.controladores;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cliper.apiBoostly.daos.MailDto;
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

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping
    public List<Usuarios> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // Método para hacer login con el mail
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody MailDto loginRequest) {
        // Busca el usuario por mail
        Usuarios usuario = usuarioService.loginUsuario(loginRequest.getMail());

        if (usuario != null) {
            // Convertir el objeto Usuarios a UsuarioDto
            UsuariosDto usuarioDto = convertirAUsuarioDto(usuario);
            return ResponseEntity.ok(usuarioDto); // Devuelve el UsuarioDto
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }
    }

    // Método para convertir Usuarios a UsuarioDto
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
            MailDto mail = new MailDto();
            mail.setMail(nuevoUsuario.getMailUsuario());
            if (usuarioService.buscarMail(mail) == null) {
            	UsuariosDto usuarioNuevoDto =  convertirAUsuarioDto(usuarioService.createUsuario(nuevoUsuario));
                
                return ResponseEntity.ok(usuarioNuevoDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rol no encontrado");
        }
    }

    @PostMapping("/generar-token")
    public ResponseEntity<String> generarToken(@RequestBody TokenContraseñaDto request) {
        usuarioToken.generarToken(request.getEmail(), request.getTokenRecuperacion());
        return ResponseEntity.ok("Token generado y enviado correctamente");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody TokenContraseñaDto request) {
        boolean actualizado = usuarioToken.actualizarContrasena(request.getTokenRecuperacion(),
                request.getNuevaContrasena());
        return actualizado ? ResponseEntity.ok("Contraseña actualizada correctamente")
                : ResponseEntity.badRequest().body("Token inválido o expirado");
    }
}
