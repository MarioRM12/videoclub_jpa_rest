package org.iesvdm.videoclub.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.iesvdm.videoclub.domain.Categoria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaCustomRespositoryJPQLImpl implements CategoriaCustomRepository{

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Categoria> queryCustomCategoria(
            Optional<String> buscarOptional,
            Optional<String> ordenarOptional,
            Pageable pageable
    ) {
        StringBuilder queryBuilder = new StringBuilder("SELECT C FROM Categoria C");

        if (buscarOptional.isPresent()) {
            queryBuilder.append(" WHERE C.nombre LIKE :nombre");
        }

        if (ordenarOptional.isPresent()) {
            if ("asc".equalsIgnoreCase(ordenarOptional.get())) {
                queryBuilder.append(" ORDER BY C.nombre ASC");
            } else if ("desc".equalsIgnoreCase(ordenarOptional.get())) {
                queryBuilder.append(" ORDER BY C.nombre DESC");
            }
        }

        Query query = em.createQuery(queryBuilder.toString(), Categoria.class);

        if (buscarOptional.isPresent()) {
            query.setParameter("nombre", "%" + buscarOptional.get() + "%");
        }

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

}
