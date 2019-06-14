package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.StatusPedido;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Darlan Silva
 */
public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Integer> {
    
    public Optional<StatusPedido> findAllByStatus(String status);
}
