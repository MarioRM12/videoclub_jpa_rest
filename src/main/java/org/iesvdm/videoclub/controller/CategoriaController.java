package org.iesvdm.videoclub.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.service.CategoriaService;
import org.iesvdm.videoclub.service.PeliculaService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping({"","/"})
    public List<Categoria> all(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String ordenar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Accediendo a todas las categorias");
        return this.categoriaService.all(buscar, ordenar, PageRequest.of(page, size));
    }

    @PostMapping({"","/"})
    public Categoria newCategoria(@RequestBody Categoria categoria) {
        return this.categoriaService.save(categoria);
    }

    @GetMapping("/{id}")
    public Categoria one(@PathVariable("id") Long id) {
        return this.categoriaService.one(id);
    }

    @PutMapping("/{id}")
    public Categoria replaceCategoria(@PathVariable("id") Long id, @RequestBody Categoria categoria) {
        return this.categoriaService.replace(id, categoria);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategoria(@PathVariable("id") Long id) {
        this.categoriaService.delete(id);
    }

    @GetMapping("/contar-peliculas")
    public Map<String, Long> countMoviesByCategory() {
        log.info("Contando películas por categoría");
        return this.categoriaService.countMoviesByCategory();
    }

    @GetMapping("/contar-peliculas/{id}")
    public Map<String, Long> countMoviesByCategoryid(@PathVariable("id") Long id) {
        log.info("Contando películas por id de categoría");
        return this.categoriaService.countMoviesByCategoryid(id);
    }

}



//OTRA FORMA DE HACER LAS BUSQUEDAS

//    @GetMapping(value = {"","/"}, params = {"!buscar", "!ordenar", "!pagina", "!tamano"})
//    public List<Categoria> all() {
//        log.info("Accediendo a todas las categorías");
//        return this.categoriaService.all();
//    }
//    @GetMapping(value = {"", "/"})
//    public ResponseEntity<Map<String, Object>> all(@RequestParam(value = "pagina", defaultValue = "0") int pagina,
//                                                   @RequestParam(value="tamano", defaultValue = "3") int tamano){
//        log.info("Accediendo a todas las categorías con paginación");
//        Map<String, Object> responseAll = this.categoriaService.all(pagina, tamano);
//        return ResponseEntity.ok(responseAll);
//    }
//    @GetMapping(value = {"","/"},  params={"!buscar", "!ordenar"})
//    public List<Categoria> all(@RequestParam("buscar") Optional<String> buscarOptional,
//                               @RequestParam("ordenar") Optional<String> ordenarOptional
//    ) {
//        log.info("Accediendo a todas las categorías con filtro buscar: %s y ordenar: %s" +
//                buscarOptional.orElse("VOID"), ordenarOptional.orElse("VOID")
//        );
//        return this.categoriaService.allByQueryFiltersStream(buscarOptional, ordenarOptional);
//    }