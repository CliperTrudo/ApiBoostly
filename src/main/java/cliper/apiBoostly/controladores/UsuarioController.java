package cliper.apiBoostly.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cliper.apiBoostly.daos.LoginRequest;
import cliper.apiBoostly.daos.MailDto;
import cliper.apiBoostly.daos.Usuarios;
import cliper.apiBoostly.daos.Roles;

import cliper.apiBoostly.dtos.SesionDto;
import cliper.apiBoostly.dtos.TokenContraseñaDto;

import cliper.apiBoostly.servicios.UsuarioService;
import cliper.apiBoostly.servicios.RolesService;
import cliper.apiBoostly.servicios.UsuarioToken;

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
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest loginRequest) {
        Usuarios usuario = usuarioService.loginUsuario(loginRequest.getMail());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/loginGoogle")
    public ResponseEntity<?> loginGoogle(@RequestBody Usuarios usuario) {
        MailDto mail = new MailDto();
        mail.setMail(usuario.getMailUsuario());
        Usuarios buscarUsu = usuarioService.buscarMail(mail);
        if (buscarUsu == null) {
            // Usuario no encontrado, crear nuevo usuario
            Usuarios nuevoUsuario = usuarioService.createUsuario(usuario);
            SesionDto sesion = new SesionDto(nuevoUsuario.getId(), nuevoUsuario.getMailUsuario(),
                    nuevoUsuario.getRol().getNombreRol());
            return ResponseEntity.ok(sesion);
        } else if (buscarUsu.getGoogleUsuario()) {
            SesionDto sesion = new SesionDto(buscarUsu.getId(), buscarUsu.getMailUsuario(),
                    buscarUsu.getRol().getNombreRol());
            return ResponseEntity.ok(sesion);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El usuario no puede ser registrado mediante Google");
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuarios usuario) {
        // Buscar el rol por idRol (idRol será el campo enviado como número)
        Roles rol = rolesService.buscarRol(usuario.getRol().getIdRol());
        if (rol != null) {
            usuario.setRol(rol);  // Asignar el rol al usuario
            MailDto mail = new MailDto();
            mail.setMail(usuario.getMailUsuario());
            if (usuarioService.buscarMail(mail) == null) {
                Usuarios nuevoUsuario = usuarioService.createUsuario(usuario);
                return ResponseEntity.ok(nuevoUsuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rol no encontrado");
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> updateUsuario(@PathVariable Long id, @RequestBody Usuarios usuarioDetails) {
        try {
            // Buscar y asignar el rol por idRol
            Roles rol = rolesService.buscarRol(usuarioDetails.getRol().getIdRol());
            if (rol != null) {
                usuarioDetails.setRol(rol);  // Asignar el rol actualizado
                Usuarios updatedUsuario = usuarioService.updateUsuario(id, usuarioDetails);
                return ResponseEntity.ok(updatedUsuario);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/buscarMail")
    public ResponseEntity<Usuarios> buscarUsuario(@RequestBody MailDto mail) {
        Usuarios usuario = usuarioService.buscarMail(mail);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUsuario(@RequestBody LoginRequest loginRequest) {
        Usuarios usuario = usuarioService.deleteUsuario(loginRequest.getMail(), loginRequest.getContrasenya());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
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
