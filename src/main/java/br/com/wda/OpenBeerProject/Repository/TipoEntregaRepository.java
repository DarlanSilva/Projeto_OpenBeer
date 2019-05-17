package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.TipoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darlan Silva
 */
@Repository
public interface TipoEntregaRepository extends JpaRepository<TipoEntrega, Integer>{
    
}
