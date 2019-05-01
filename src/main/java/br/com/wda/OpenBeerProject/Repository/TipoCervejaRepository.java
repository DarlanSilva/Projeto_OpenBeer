package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.TipoCerveja;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
public class TipoCervejaRepository {
    
        @PersistenceContext
    private EntityManager entityManager;

    public List<TipoCerveja> findAll(Integer offset, Integer quantidade) {
        Query jpqlQuery
                = entityManager.createNamedQuery("TipoCerveja.findAll")
                        .setFirstResult(offset)
                        .setMaxResults(quantidade);
        return jpqlQuery.getResultList();
    }

    public TipoCerveja findById(Long id) {
        Query jpqlQuery
                = entityManager
                        .createNamedQuery("TipoCerveja.findById")
                        .setParameter("idTipo", id);
        TipoCerveja tipoCerveja = (TipoCerveja) jpqlQuery.getSingleResult();
        return tipoCerveja;
    }

    @Transactional
    public void save(TipoCerveja tipoCerveja) {
        if (tipoCerveja.getId() == null) {
            // Salva um novo produto
            entityManager.persist(tipoCerveja);
        } else {
            // Atualiza um produto existente
            entityManager.merge(tipoCerveja);
        }
    }

    @Transactional
    public void delete(Long id) {
        // TEM QUE FAZER CONSULTA PARA ESTAR ASSOCIADO AO
        // ENTITY MANAGER (ATTACHED)
        TipoCerveja tipoCerveja = entityManager.find(TipoCerveja.class, id);
        entityManager.remove(tipoCerveja);
    }
    
}
