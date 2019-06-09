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
public interface LoginRepository extends JpaRepository<Login, Integer>{
    
    public Optional<Login> findByEmail(String email);
    
    @Query("Select l from Login l left join Cliente c on l.id = c.login where c.id = :clienteID")
    public Optional<Login> findByClienteID(Integer clienteID);
}
