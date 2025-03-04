package cliper.apiBoostly.servicios;

import cliper.apiBoostly.daos.Categoria;
import cliper.apiBoostly.dtos.CategoriaDto;
import cliper.apiBoostly.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Método para listar todas las categorías
    public List<CategoriaDto> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                         .map(categoria -> new CategoriaDto(categoria.getIdCategoria(), categoria.getNombreCategoria(), categoria.getDescripcionCategoria()))
                         .collect(Collectors.toList());
    }

    // Método para obtener una categoría por ID
    public Categoria obtenerCategoriaPorId(Long idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria).orElse(null);
        if (categoria != null) {
            return categoria;
        }
        return null;
    }

    // Método para crear una nueva categoría
    public CategoriaDto crearCategoria(CategoriaDto categoriaDto) {
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(categoriaDto.getNombreCategoria());
        categoria.setDescripcionCategoria(categoriaDto.getDescripcionCategoria());
        categoria = categoriaRepository.save(categoria);
        return new CategoriaDto(categoria.getIdCategoria(), categoria.getNombreCategoria(), categoria.getDescripcionCategoria());
    }
}
