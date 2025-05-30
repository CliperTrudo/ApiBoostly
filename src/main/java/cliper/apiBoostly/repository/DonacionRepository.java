package cliper.apiBoostly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cliper.apiBoostly.daos.Donacion;

import java.util.Optional;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    Optional<Donacion> findByOrderId(String orderId);
}