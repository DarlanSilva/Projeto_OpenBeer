package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import java.math.BigDecimal;
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
public interface CervejaRepository extends JpaRepository<Cerveja, Integer> {

    public Optional<Cerveja> findByNome(String nome);

    public Optional<Cerveja> findAllByDestaque(boolean destaque);

    public Optional<Cerveja> findAllByTipoCerveja(Integer tipoCerveja);

    public Optional<Cerveja> findAllByNomeOrderByNomeDesc(String nome);

    public Optional<Cerveja> findAllByNomeOrderByNome(String nome);

    public Optional<Cerveja> findAllByValorCervejaOrderByValorCerveja(BigDecimal valorCerveja);

    public Optional<Cerveja> findAllByValorCervejaOrderByValorCervejaDesc(BigDecimal valorCerveja);

}
