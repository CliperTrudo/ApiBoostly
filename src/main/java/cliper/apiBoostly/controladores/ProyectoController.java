package cliper.apiBoostly.controladores;

import cliper.apiBoostly.daos.Categoria;
import cliper.apiBoostly.daos.Proyectos;

import cliper.apiBoostly.dtos.ProyectoDto;
import cliper.apiBoostly.dtos.CategoriaDto;
import cliper.apiBoostly.servicios.ProyectoService;
import cliper.apiBoostly.servicios.CategoriaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

	@Autowired
	private ProyectoService proyectoService;

	@Autowired
	private CategoriaService categoriaService;

	/**
	 * Crea un nuevo proyecto. Se asigna automáticamente el usuario logueado y la
	 * fecha de inicio se establece en el momento de la creación.
	 */
	@PostMapping
	public ResponseEntity<?> crearProyecto(@RequestBody ProyectoDto proyectoDto) {
		// Asignar el usuario logueado y la fecha de inicio
		System.out.println(proyectoDto.toString());
		proyectoDto.setFechaInicioProyecto(LocalDateTime.now());

		Categoria categiriaEncontradaCategoria = categoriaService.obtenerCategoriaPorId(proyectoDto.getIdCategoria());
		CategoriaDto categoria = new CategoriaDto(categiriaEncontradaCategoria.getIdCategoria(),
				categiriaEncontradaCategoria.getNombreCategoria(),
				categiriaEncontradaCategoria.getDescripcionCategoria());

		if (categoria.getIdCategoria() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoría no válida");
		}

		// Llamar al servicio para crear el proyecto
		Proyectos proyectoCreado = proyectoService.crearProyecto(proyectoDto);

		// Convertir la entidad Proyectos a ProyectoDto antes de devolverla
		ProyectoDto proyectoDtoResponse = convertirAProyectoDto(proyectoCreado);
		return ResponseEntity.ok(proyectoDtoResponse);
	}

	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<List<ProyectoDto>> obtenerProyectosPorUsuario(@PathVariable Long idUsuario) {
		List<Proyectos> proyectos = proyectoService.obtenerProyectosPorUsuario(idUsuario);
		List<ProyectoDto> proyectosDto = proyectos.stream().map(this::convertirAProyectoDto)
				.collect(Collectors.toList());
		if (!proyectos.isEmpty()) {
			return ResponseEntity.ok(proyectosDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProyectoDto> obtenerProyectoPorId(@PathVariable Long id) {
		System.out.println("hola");
		Proyectos proyecto = proyectoService.obtenerProyectoPorId(id);
		ProyectoDto proyectoDtoResponse = convertirAProyectoDto(proyecto);
		if (proyecto != null) {
			return ResponseEntity.ok(proyectoDtoResponse);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<List<ProyectoDto>> obtenerProyectos() {
		List<Proyectos> proyectos = proyectoService.listarProyectos();
		List<ProyectoDto> proyectosDto = proyectos.stream().map(this::convertirAProyectoDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(proyectosDto);
	}

	@GetMapping("/categoria/{idCategoria}")
	public ResponseEntity<List<ProyectoDto>> obtenerProyectosPorCategoria(@PathVariable Long idCategoria) {
		List<Proyectos> proyectos = proyectoService.obtenerProyectosPorCategoria(idCategoria);
		List<ProyectoDto> proyectosDto = proyectos.stream().map(this::convertirAProyectoDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(proyectosDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Proyectos> actualizarProyecto(@PathVariable Long id, @RequestBody ProyectoDto proyectoDto) {
		Proyectos proyectoActualizado = proyectoService.actualizarProyecto(id, proyectoDto);
		return (proyectoActualizado != null) ? ResponseEntity.ok(proyectoActualizado)
				: ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> eliminarProyecto(@PathVariable Long id) {
		boolean respuesta = proyectoService.eliminarProyecto(id);
		return (respuesta != false) ? ResponseEntity.ok(respuesta) : ResponseEntity.notFound().build();
	}

	// Método para convertir la entidad Proyectos a ProyectoDto
	private ProyectoDto convertirAProyectoDto(Proyectos proyecto) {
		ProyectoDto proyectoDto = new ProyectoDto();
		proyectoDto.setIdProyecto(proyecto.getIdProyecto());
		proyectoDto.setIdUsuario(proyecto.getUsuario().getId());
		proyectoDto.setNombreProyecto(proyecto.getNombreProyecto());
		proyectoDto.setDescripcionProyecto(proyecto.getDescripcionProyecto());
		proyectoDto.setImagen1Proyecto(proyecto.getImagen1Proyecto());
		proyectoDto.setImagen2Proyecto(proyecto.getImagen2Proyecto());
		proyectoDto.setImagen3Proyecto(proyecto.getImagen3Proyecto());
		proyectoDto.setFechaInicioProyecto(proyecto.getFechaInicioProyecto());
		proyectoDto.setFechaFinalizacionProyecto(proyecto.getFechaFinalizacionProyecto());
		proyectoDto.setMetaRecaudacionProyecto(proyecto.getMetaRecaudacionProyecto());
		proyectoDto.setEstadoProyecto(proyecto.getEstadoProyecto());
		proyectoDto.setIdCategoria(proyecto.getCategoriaProyecto().getIdCategoria()); // Ahora se usa la id de la
																						// categoría
		return proyectoDto;
	}
}
