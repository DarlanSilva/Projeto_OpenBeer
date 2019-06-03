package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Login;
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
public interface LoginRepository extends JpaRepository<Login, Long>{
    
    Login findByEmail(String email);
    
}
