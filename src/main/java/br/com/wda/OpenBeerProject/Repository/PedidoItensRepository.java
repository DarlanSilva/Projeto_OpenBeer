package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.PedidoItens;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darlan Silva
 */
@Repository
public interface PedidoItensRepository extends JpaRepository<PedidoItens, Integer> {

    @Query("Select i from PedidoItens i "
            + "left join Pedido p on i.pedido = p.id "
            + "left join Cliente c on c.id = p.cliente where c.id = :clienteID order by p.id desc")
    public List<PedidoItens> findAllByClienteID(Integer clienteID);

    @Query("Select i from PedidoItens i where i.dhInclusao >= :dhInclusaoIni and i.dhInclusao < :dhInclusaoFin")
    public List<PedidoItens> findAllByDhInclusao(LocalDateTime dhInclusaoIni, LocalDateTime dhInclusaoFin);

    @Query("Select i from PedidoItens i where i.dhInclusao >= :dhInclusaoIni")
    public List<PedidoItens> findAllByDhInclusaoIni(LocalDateTime dhInclusaoIni);

    @Query("Select i from PedidoItens i where i.dhInclusao <= :dhInclusaoFin")
    public List<PedidoItens> findAllByDhInclusaoFin(LocalDateTime dhInclusaoFin);

    @Query("Select i from PedidoItens i")
    public List<PedidoItens> findAllByDhInclusao();
}
