package org.iesvdm.videoclub;

import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VideoclubApplicationTests {

    @Autowired
    public PeliculaRepository peliculaRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Test
    void contextLoads() {

        System.out.println("Conteo: " +peliculaRepository.countByCategorias(categoriaRepository.getReferenceById(1L)));

        System.out.println("Conteo: " +peliculaRepository.countByCategorias_id(1L));

    }

}
