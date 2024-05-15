package org.iesvdm.videoclub.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.service.PeliculaService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/peliculas")
public class PeliculaController {
    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping({"","/"})
    public List<Pelicula> all() {
        log.info("Accediendo a todas las películas");
        return this.peliculaService.all();
    }

//    @GetMapping(value = {"", "/"}, params = {"pagina", "tamanio"})
//    public ResponseEntity<Map<String, Object>> all(
//            @RequestParam(value = "pagina", defaultValue = "0") int pagina
//            , @RequestParam(value = "tamanio", defaultValue = "3") int tamanio){
//
//        log.info("Accediendo a todas las películas con paginación");
//
//        Map<String, Object> responseAll = this.peliculaService.all(pagina, tamanio);
//
//        return ResponseEntity.ok(responseAll);
//    }
//
//    @GetMapping(value = {"", "/"}, params = {"ordenacion"})
//    public ResponseEntity<Map<String, Object>> all(
//            @RequestParam(value = "ordenacion") ArrayList<String> ordenacion){
//
//        log.info("Procesando array de parámetros de ordenación");
//        List<String> posicion0 = Arrays.stream(ordenacion.get(0).split(",")).toList();
//        List<String> posicion1 = Arrays.stream(ordenacion.get(1).split(",")).toList();
//
//        if(posicion0.size() == 1 && posicion1.size() == 1){
//            log.info("Recibido 1 orden y 1 sentido");
//            return ResponseEntity.ok(this.peliculaService.allOrdered(posicion0.getFirst(), posicion1.getFirst()));
//        }else if(posicion0.size() == 2 && posicion1.size() == 2){
//            log.info("Recibidos 2 campos y 2 sentidos");
//            return ResponseEntity.ok(this.peliculaService.allOrdered(posicion0.get(0), posicion0.get(1), posicion1.get(0), posicion1.get(1)));
//        }else{
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @PostMapping({"","/"})
    public Pelicula newPelicula(@RequestBody Pelicula pelicula) {
        return this.peliculaService.save(pelicula);
    }

    @GetMapping("/{id}")
    public Pelicula one(@PathVariable("id") Long id) {
        return this.peliculaService.one(id);
    }

    @PutMapping("/{id}")
    public Pelicula replacePelicula(@PathVariable("id") Long id, @RequestBody Pelicula pelicula) {
        return this.peliculaService.replace(id, pelicula);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePelicula(@PathVariable("id") Long id) {
        this.peliculaService.delete(id);
    }


}