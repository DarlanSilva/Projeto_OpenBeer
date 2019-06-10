package br.com.wda.OpenBeerProject.Repository;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.TipoCerveja;
import java.util.List;
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
public interface CervejaRepository extends JpaRepository<Cerveja, Integer> {

    @Query("Select c from Cerveja c where c.nome like %:nome%")
    public List<Cerveja> findByNome(String nome);

    public List<Cerveja> findAllByDestaque(boolean destaque);

    @Query("Select c from Cerveja c left join TipoCerveja t on c.tipoCerveja = t.id where t.id = :tipoCervejaID")
    public List<Cerveja> findByTipoCerveja(Integer tipoCervejaID);
    
    public List<Cerveja> findByTipoCerveja(TipoCerveja tipoCerveja);

}
