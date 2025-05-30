package cliper.apiBoostly.servicios;

import cliper.apiBoostly.daos.Donacion;
import cliper.apiBoostly.repository.*;
import cliper.apiBoostly.dtos.DonacionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
public class DonacionService {

    private final DonacionRepository repositorio;

    public DonacionService(DonacionRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional
    public Donacion crearDonacion(DonacionDto dto) {
        Donacion d = new Donacion();
        d.setOrderId(dto.getOrderId());
        d.setImporte(dto.getImporte());
        d.setMoneda(dto.getMoneda());
        d.setEstado("CREATED");
        d.setCreadoEn(Instant.now());
        return repositorio.save(d);
    }

    @Transactional
    public Donacion actualizarDonacion(DonacionDto dto) {
        Donacion d = repositorio.findByOrderId(dto.getOrderId())
            .orElseThrow(() -> new RuntimeException("Donación no encontrada: " + dto.getOrderId()));
        if (dto.getEstado() != null)       d.setEstado(dto.getEstado());
        if (dto.getPayerId() != null)      d.setPayerId(dto.getPayerId());
        if (dto.getPayerEmail() != null)   d.setPayerEmail(dto.getPayerEmail());
        if (dto.getCapturadoEn() != null)  d.setCapturadoEn(dto.getCapturadoEn());
        return repositorio.save(d);
    }
}
