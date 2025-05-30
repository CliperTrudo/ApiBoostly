package cliper.apiBoostly.controladores;

import cliper.apiBoostly.dtos.DonacionDto;
import cliper.apiBoostly.servicios.DonacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

	@Autowired
    private DonacionService servicio;

    @PostMapping
    public ResponseEntity<?> crear(@Validated @RequestBody DonacionDto dto) {
        var donacionCreada = servicio.crearDonacion(dto);
        return ResponseEntity.ok(donacionCreada);
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@Validated @RequestBody DonacionDto dto) {
        var donacionActualizada = servicio.actualizarDonacion(dto);
        return ResponseEntity.ok(donacionActualizada);
    }
}
