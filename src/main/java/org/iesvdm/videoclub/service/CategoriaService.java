package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.exception.PeliculaNotFoundException;
import org.iesvdm.videoclub.repository.CategoriaCustomRespositoryJPQLImpl;
import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoriaService {


    private final CategoriaRepository categoriaRepository;


    private final PeliculaRepository peliculaRepository;


    private final CategoriaCustomRespositoryJPQLImpl categoriaCustomRespositoryJPQLImpl;

    public CategoriaService(CategoriaRepository categoriaRepository, PeliculaRepository peliculaRepository, CategoriaCustomRespositoryJPQLImpl categoriaCustomRespositoryJPQLImpl) {
        this.categoriaRepository = categoriaRepository;
        this.peliculaRepository = peliculaRepository;
        this.categoriaCustomRespositoryJPQLImpl = categoriaCustomRespositoryJPQLImpl;
    }

    public List<Categoria> all() {
        return this.categoriaRepository.findAll();
    }

    public List<Categoria> all(String buscar, String ordenar, Pageable pageable) {
        return categoriaCustomRespositoryJPQLImpl.queryCustomCategoria(
                Optional.ofNullable(buscar),
                Optional.ofNullable(ordenar),
                pageable
        );
    }

    public Categoria save(Categoria categoria) {
        return this.categoriaRepository.save(categoria);
    }

    public Categoria one(Long id) {
        return this.categoriaRepository.findById(id)
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    public Categoria replace(Long id, Categoria categoria) {

        return this.categoriaRepository.findById(id).map( p -> (id.equals(categoria.getId())  ?
                        this.categoriaRepository.save(categoria) : null))
                .orElseThrow(() -> new PeliculaNotFoundException(id));

    }

    public void delete(Long id) {
        this.categoriaRepository.findById(id).map(p -> {this.categoriaRepository.delete(p);
                    return p;})
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    public Map<String, Long> countMoviesByCategory() {
        List<Categoria> categorias = categoriaRepository.findAll();
        Map<String, Long> moviesCountByCategory = new HashMap<>();

        for (Categoria categoria : categorias) {
            Long movieCount = peliculaRepository.countByCategorias(categoria);
            moviesCountByCategory.put(categoria.getNombre(), movieCount);
        }

        return moviesCountByCategory;
    }

    public Map<String, Long> countMoviesByCategoryid(Long id) {
        List<Categoria> categorias = categoriaRepository.findAll();
        Map<String, Long> moviesCountByCategory = new HashMap<>();

        for (Categoria categoria : categorias) {
            Long movieCount = peliculaRepository.countByCategorias_id(id);
            moviesCountByCategory.put(categoria.getNombre(), movieCount);
        }

        return moviesCountByCategory;
    }

}
