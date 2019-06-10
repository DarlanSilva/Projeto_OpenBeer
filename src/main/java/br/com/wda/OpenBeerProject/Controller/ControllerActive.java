package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.TipoEntrega;
import br.com.wda.OpenBeerProject.Repository.TipoEntregaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Darlan Silva
 */
@ControllerAdvice(annotations = Controller.class)
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class ControllerActive {

    @Autowired
    private CarrinhoCompras carrinho;

    @Autowired
    private TipoEntregaRepository tipoEntregaRepo;

    @ModelAttribute("carrinhoCompras")
    @Cacheable(value = "tipos-de-cerveja")
    public CarrinhoCompras getItens() {
        List<TipoEntrega> tipoEntrega = tipoEntregaRepo.findAll();
//        carrinho.setIdTipoEntrega(1);
//        carrinho.setPrazoEntrega(tipoEntrega.get(0).getPrazoEntrega());
//        carrinho.setValorEntrega(tipoEntrega.get(0).getValorEntrega());
        carrinho.setTipoEntrega(tipoEntrega);
        return carrinho;
    }

    @ModelAttribute("quantidadeCarrinho")
    public int getQuantidadeCarrinho() {
        return carrinho.getQuantidade();
    }

//    @ModelAttribute("tipoEntrega")
//    public List<TipoEntrega> getTipoEntregas() {
//        return tipoEntregaRepo.findAll();
//    }
}
