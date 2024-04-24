package org.iesvdm.videoclub.repository;

import org.iesvdm.videoclub.domain.Categoria;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaCustomRepository  {

    List<Categoria> queryCustomCategoria(
            Optional<String> buscarOptional,
            Optional<String> ordenarOptional,
            org.springframework.data.domain.Pageable pageable
    );

}
