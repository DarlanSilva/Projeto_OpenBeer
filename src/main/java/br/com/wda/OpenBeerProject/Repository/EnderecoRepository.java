package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Endereco;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Darlan Silva
 */

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer>{
    
    @Query("Select e from Endereco e left join Cliente c on e.idCliente = c.id where c.id = ?1")
    public Optional<Endereco> findByClienteId(Integer clineteId);
}
