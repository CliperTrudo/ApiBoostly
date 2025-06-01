package cliper.apiBoostly.controladores;

import cliper.apiBoostly.dtos.DonacionDto;
import cliper.apiBoostly.servicios.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DonacionController
 *
 * Controlador REST que maneja las peticiones relacionadas con Donaciones.
 * Proporciona endpoints para:
 * - Crear una donación (POST /donaciones)
 * - Listar donaciones pendientes de un proyecto (GET /donaciones/proyecto/{id}?estado=XYZ)
 * - Actualizar estado de una donación (PUT /donaciones/{id})
 */
@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

    private final DonacionService donacionService;

    @Autowired
    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    /**
     * Crea una nueva donación (estado inicial PENDIENTE_AUTORIZACION).
     *
     * Ejemplo de JSON a enviar en el body:
     * {
     *   "usuarioId": 5,
     *   "proyectoId": 10,
     *   "authorizationId": "AUTHID123",
     *   "orderId": "ORDERID123",
     *   "amount": 25.00,
     *   "estado": "PENDIENTE_AUTORIZACION"
     * }
     *
     * @param donacionDto DTO con los datos de la donación a crear.
     * @return ResponseEntity con la donación creada y estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<DonacionDto> crearDonacion(@RequestBody DonacionDto donacionDto) {
        DonacionDto creada = donacionService.crearDonacion(donacionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    /**
     * Obtiene la lista de donaciones de un proyecto con un estado dado.
     * Por ejemplo: GET /donaciones/proyecto/10?estado=PENDIENTE_AUTORIZACION
     *
     * @param proyectoId ID del proyecto.
     * @param estado     (Opcional) Estado de la donación. Si no se envía, 
     *                   podrías devolver todas (o manejarlo como error).
     * @return Lista de DonacionDto con estado 200 OK.
     */
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<DonacionDto>> obtenerPorProyectoYEstado(
            @PathVariable Long proyectoId,
            @RequestParam(name = "estado", required = false) String estado) {

        if (estado == null || estado.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<DonacionDto> lista = donacionService.buscarPorProyectoYEstado(proyectoId, estado);
        return ResponseEntity.ok(lista);
    }

    /**
     * Actualiza una donación existente (por ejemplo, cambia su estado a "CAPTURADA" o "ANULADA").
     *
     * Ejemplo de JSON a enviar en el body:
     * {
     *   "idDonacion": 123,
     *   "estado": "CAPTURADA"
     * }
     *
     * @param idDonacion  ID de la donación a actualizar.
     * @param donacionDto DTO con los datos a actualizar (al menos el estado).
     * @return ResponseEntity con la donación actualizada y estado 200 OK.
     */
    @PutMapping("/{idDonacion}")
    public ResponseEntity<DonacionDto> actualizarDonacion(
            @PathVariable Long idDonacion,
            @RequestBody DonacionDto donacionDto) {

        if (!idDonacion.equals(donacionDto.getIdDonacion())) {
            return ResponseEntity.badRequest().build();
        }

        DonacionDto actualizada = donacionService.actualizarDonacion(donacionDto);
        return ResponseEntity.ok(actualizada);
    }
}
