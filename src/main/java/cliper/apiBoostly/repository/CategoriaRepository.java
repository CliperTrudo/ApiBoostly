package cliper.apiBoostly.repository;

import cliper.apiBoostly.daos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Puedes agregar métodos personalizados si es necesario
}
