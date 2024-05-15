package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.exception.PeliculaNotFoundException;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public List<Pelicula> all() {
        return this.peliculaRepository.findAll();
    }

    public List<Pelicula> all(Sort sort, Pageable pageable) {
        if (sort != null && pageable != null) {
            // Si se proporciona orden y paginado
            Page<Pelicula> peliculaPage = peliculaRepository.findAll(pageable);
            return peliculaPage.getContent();
        } else if (sort != null) {
            // Si solo se proporciona orden
            return peliculaRepository.findAll(sort);
        } else if (pageable != null) {
            // Si solo se proporciona paginado
            Page<Pelicula> peliculaPage = peliculaRepository.findAll(pageable);
            return peliculaPage.getContent();
        } else {
            // Si no se proporciona ni orden ni paginado
            return peliculaRepository.findAll();
        }
    }

    public Map<String, Object> all(int pagina, int tamanio){

        Pageable paginado = PageRequest.of(pagina, tamanio, Sort.by("id").ascending());
        Page<Pelicula> pageAll = this.peliculaRepository.findAll(paginado);

        Map<String, Object> response = new HashMap<>();

        response.put("peliculas", pageAll.getContent());
        response.put("currentpage", pageAll.getNumber());
        response.put("totalItems", pageAll.getTotalElements());
        response.put("totalPages", pageAll.getTotalPages());

        return response;
    }

    public Map<String, Object> allOrdered(String campo, String sentido){
        System.out.println("Ordenando por campo: " + campo + ", sentido: " + sentido);

        // Validación básica del campo de ordenación
        if (!isValidField(campo)) {
            throw new IllegalArgumentException("Campo de ordenación no válido: " + campo);
        }

        Pageable paginado = PageRequest.of(0, 10, Sort.by(campo).descending()); // Ordenación descendente por defecto

        // Verifica el sentido de la ordenación
        if (sentido != null && sentido.equalsIgnoreCase("asc")) {
            paginado = PageRequest.of(0, 10, Sort.by(campo).ascending());
        }

        // Realiza la consulta con la paginación y la ordenación proporcionadas
        Page<Pelicula> pageAll = this.peliculaRepository.findAll(paginado);

        // Construye la respuesta con los datos obtenidos
        Map<String, Object> response = new HashMap<>();
        response.put("peliculas", pageAll.getContent());
        response.put("currentPage", pageAll.getNumber());
        response.put("totalItems", pageAll.getTotalElements());
        response.put("totalPages", pageAll.getTotalPages());
        return response;
    }

    // Método para validar si el campo de ordenación es válido
    private boolean isValidField(String campo) {
        // Aquí puedes agregar lógica para verificar si el campo es válido en tu entidad Pelicula
        // Por ejemplo, podrías verificar si existe como propiedad en la entidad Pelicula
        // En este ejemplo, asumiré que cualquier cadena es un campo válido para simplificar
        return campo != null && !campo.isEmpty();
    }

    public Map<String, Object> allOrdered(String campo1, String sentido1, String campo2, String sentido2){

        Sort ordenacion = Sort.by(
                Sort.Order.by(campo1).with(
                        sentido1.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC
                ),
                Sort.Order.by(campo2).with(
                        sentido2.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC
                )
        );

        Pageable paginado = PageRequest.of(0, 10, ordenacion);

        Page<Pelicula> pageAll = this.peliculaRepository.findAll(paginado);

        Map<String, Object> response = new HashMap<>();
        response.put("peliculas", pageAll.getContent());
        response.put("currentPage", pageAll.getNumber());
        response.put("totalItems", pageAll.getTotalElements());
        response.put("totalPages", pageAll.getTotalPages());
        return response;
    }

    public Pelicula save(Pelicula pelicula) {
        return this.peliculaRepository.save(pelicula);
    }

    public Pelicula one(Long id) {
        return this.peliculaRepository.findById(id)
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    public Pelicula replace(Long id, Pelicula pelicula) {

        return this.peliculaRepository.findById(id).map( p -> (id.equals(pelicula.getIdPelicula())  ?
                                                            this.peliculaRepository.save(pelicula) : null))
                .orElseThrow(() -> new PeliculaNotFoundException(id));

    }

    public void delete(Long id) {
        this.peliculaRepository.findById(id).map(p -> {this.peliculaRepository.delete(p);
                                                        return p;})
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

}