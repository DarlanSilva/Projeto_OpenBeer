package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Pedido;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darlan Silva
 */

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Integer>{
    
    @Query("Select p from Pedido p left join Cliente c on p.cliente = c.id where c.id = :clienteID order by p.id desc")
    public List<Pedido> findAllByClienteID(Integer clienteID);
    
    @Query("Select p from Pedido p where p.dhInclusao >= :dhInclusaoIni and p.dhInclusao < :dhInclusaoFin")
    public List<Pedido> findAllByDhInclusao(LocalDateTime dhInclusaoIni, LocalDateTime dhInclusaoFin);
    
    @Query("Select p from Pedido p")
    public List<Pedido> findAllByDhInclusao();

}
