package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
}
