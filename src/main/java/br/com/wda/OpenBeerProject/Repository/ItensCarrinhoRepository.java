package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.ItensCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darlan Silva
 */
@Repository
public interface ItensCarrinhoRepository extends JpaRepository<ItensCarrinho, Integer>{
    
}
