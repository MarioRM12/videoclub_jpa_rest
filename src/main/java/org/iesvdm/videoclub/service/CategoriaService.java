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

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private final CategoriaRepository categoriaRepository;

    @Autowired
    private final CategoriaCustomRespositoryJPQLImpl categoriaCustomRespositoryJPQLImpl;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaCustomRespositoryJPQLImpl categoriaCustomRespositoryJPQLImpl) {
        this.categoriaRepository = categoriaRepository;
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

}
