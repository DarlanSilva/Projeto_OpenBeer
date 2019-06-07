package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Cliente;
import br.com.wda.OpenBeerProject.Entity.Login;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    
    @Query("Select c from Cliente c left join Login l on l.id = c.login where l.id = ?1")
    public Optional<Cliente> findByLogin(Integer login);
    
}
