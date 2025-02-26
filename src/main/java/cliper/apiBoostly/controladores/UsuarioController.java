package cliper.apiBoostly.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cliper.apiBoostly.daos.MailDto;
import cliper.apiBoostly.daos.Roles;
import cliper.apiBoostly.daos.Usuarios;

import cliper.apiBoostly.dtos.SesionDto;
import cliper.apiBoostly.dtos.TokenContraseñaDto;
import cliper.apiBoostly.dtos.UsuariosDto;

import cliper.apiBoostly.servicios.UsuarioService;
import cliper.apiBoostly.servicios.RolesService;
import cliper.apiBoostly.servicios.UsuarioToken;

import java.util.Base64;
import java.sql.Timestamp;
import java.util.List;

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

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody MailDto loginRequest) {
        Usuarios usuario = usuarioService.loginUsuario(loginRequest.getMail());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }
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

            // Limpiar y convertir la imagen de Base64 a byte[]
            if (usuarioDto.getImgUsuario() != null) {
                String base64Image = usuarioDto.getImgUsuario();
                // Eliminar la cabecera si existe
                if (base64Image.startsWith("data:image")) {
                    base64Image = base64Image.split(",")[1];  // Eliminar "data:image/png;base64,"
                }
                try {
                    byte[] imgBytes = Base64.getDecoder().decode(base64Image); // Convertir de Base64 a byte[]
                    nuevoUsuario.setImgUsuario(imgBytes); // Establecer los datos binarios de la imagen
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Imagen en Base64 no válida");
                }
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
                nuevoUsuario = usuarioService.createUsuario(nuevoUsuario);
                return ResponseEntity.ok(nuevoUsuario);
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
        boolean actualizado = usuarioToken.actualizarContrasena(request.getTokenRecuperacion(), request.getNuevaContrasena());
        return actualizado ? ResponseEntity.ok("Contraseña actualizada correctamente")
                : ResponseEntity.badRequest().body("Token inválido o expirado");
    }
}
