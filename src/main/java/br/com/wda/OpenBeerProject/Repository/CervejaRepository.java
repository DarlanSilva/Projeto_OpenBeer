/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
@Repository
public class CervejaRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cerveja> findAll(Integer offset, Integer quantidade) {
        Query jpqlQuery
                = entityManager.createNamedQuery("Cerveja.findAll")
                        .setFirstResult(offset)
                        .setMaxResults(quantidade);
        return jpqlQuery.getResultList();
    }

    public List<Cerveja> findByTipo(List<Integer> idTipo) {
        Query jpqlQuery
                = entityManager
                        .createNamedQuery("Cerveja.findByTipo")
                        .setParameter("idTipo", idTipo);
        return jpqlQuery.getResultList();
    }

    public Cerveja findById(Long id) {
        Query jpqlQuery
                = entityManager
                        .createNamedQuery("Cerveja.findById")
                        .setParameter("idCerveja", id);
        Cerveja cerveja = (Cerveja) jpqlQuery.getSingleResult();
        return cerveja;
    }

    @Transactional
    public void save(Cerveja cerveja) {
        if (cerveja.getId() == null) {
            // Salva um novo produto
            entityManager.persist(cerveja);
        } else {
            // Atualiza um produto existente
            entityManager.merge(cerveja);
        }
    }

    @Transactional
    public void delete(Long id) {
        // TEM QUE FAZER CONSULTA PARA ESTAR ASSOCIADO AO
        // ENTITY MANAGER (ATTACHED)
        Cerveja cerveja = entityManager.find(Cerveja.class, id);
        entityManager.remove(cerveja);
    }
}
