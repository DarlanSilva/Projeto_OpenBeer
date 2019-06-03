package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Cliente;
import java.util.Optional;
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
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    public Optional<Cliente> findByNomeCompleto(String nomeCompleto);
    
}
