package cliper.apiBoostly.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cliper.apiBoostly.daos.LoginRequest;
import cliper.apiBoostly.daos.MailDto;
import cliper.apiBoostly.daos.Usuarios;

import cliper.apiBoostly.dtos.SesionDto;
import cliper.apiBoostly.dtos.TokenContraseñaDto;

import cliper.apiBoostly.servicios.UsuarioService;
import cliper.apiBoostly.servicios.UsuarioToken;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	private final UsuarioToken usuarioToken;

	@Autowired
	public UsuarioController(UsuarioService usuarioService, UsuarioToken usuarioToken) {
		this.usuarioService = usuarioService;
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
			System.out.println("si");
			return ResponseEntity.ok(usuario);
		} else {
			System.out.println("no");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
		}
	}

	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PostMapping("/loginGoogle")
	public ResponseEntity<?> loginGoogle(@RequestBody Usuarios usuario) {

		System.out.println(usuario.toString());
		MailDto mail = new MailDto();
		mail.setMail(usuario.getMailUsuario());
		Usuarios buscarUsu = usuarioService.buscarMail(mail);
		if (buscarUsu == null) {
			System.out.println("No se encontro al usuario en la base de datos, vamos a darle de alta");

			Usuarios nuevoUsuario = usuarioService.createUsuario(usuario);
			SesionDto sesion = new SesionDto(nuevoUsuario.getId(), nuevoUsuario.getMailUsuario(),
					nuevoUsuario.getRolUsuario());
			return ResponseEntity.ok(sesion);
		} else if (buscarUsu.getGoogleUsuario() == true) {
			System.out.println("Se encontro al usuario, login");
			SesionDto sesion = new SesionDto(buscarUsu.getId(), buscarUsu.getMailUsuario(), buscarUsu.getRolUsuario());
			return ResponseEntity.ok(sesion);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("El usuario no puede ser registrado mediante google");
		}
	}

	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PostMapping
	public ResponseEntity<?> createUsuario(@RequestBody Usuarios usuario) {
		MailDto mail = new MailDto();
		mail.setMail(usuario.getMailUsuario());
		if (usuarioService.buscarMail(mail) == null) {
			Usuarios nuevoUsuario = usuarioService.createUsuario(usuario);
			SesionDto sesion = new SesionDto(nuevoUsuario.getId(), nuevoUsuario.getMailUsuario(),
					nuevoUsuario.getRolUsuario());
			return ResponseEntity.ok(sesion);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PutMapping("/{id}")
	public ResponseEntity<Usuarios> updateUsuario(@PathVariable Long id, @RequestBody Usuarios usuarioDetails) {
		try {
			Usuarios updatedUsuario = usuarioService.updateUsuario(id, usuarioDetails);
			return ResponseEntity.ok(updatedUsuario);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PostMapping("/buscarMail")
	public ResponseEntity<Usuarios> BuscarUsuario(@RequestBody MailDto mail) {
		System.out.println(mail.toString());
		Usuarios usuario = usuarioService.buscarMail(mail);

		return ResponseEntity.status(HttpStatus.OK).body(usuario);

	}

	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteClub(@RequestBody LoginRequest loginRequest) {
		System.out.print(loginRequest.toString());
		Usuarios usuario = usuarioService.deleteUsuario(loginRequest.getMail(), loginRequest.getContrasenya());
		if (usuario != null) {
			System.out.println("si");
			return ResponseEntity.ok(usuario);

		} else {
			System.out.println("no");
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
		boolean actualizado = usuarioToken.actualizarContrasena(request.getTokenRecuperacion(),
				request.getNuevaContrasena());
		return actualizado ? ResponseEntity.ok("Contraseña actualizada correctamente")
				: ResponseEntity.badRequest().body("Token inválido o expirado");
	}

}
