package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.TipoCerveja;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
@Repository
public interface TipoCervejaRepository extends JpaRepository<TipoCerveja, Integer> {
     
    
}
