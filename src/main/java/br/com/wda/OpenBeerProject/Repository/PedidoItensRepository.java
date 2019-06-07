package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.PedidoItens;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darlan Silva
 */
@Repository
public interface PedidoItensRepository extends JpaRepository<PedidoItens, Integer>{
    
}
