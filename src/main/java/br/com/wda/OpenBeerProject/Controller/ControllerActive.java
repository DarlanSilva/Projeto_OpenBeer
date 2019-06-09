package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.Cliente;
import br.com.wda.OpenBeerProject.Entity.Endereco;
import br.com.wda.OpenBeerProject.Entity.TipoEntrega;
import br.com.wda.OpenBeerProject.Repository.ClienteRepository;
import br.com.wda.OpenBeerProject.Repository.EnderecoRepository;
import br.com.wda.OpenBeerProject.Repository.TipoEntregaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public CarrinhoCompras getItens() {
        carrinho.setTipoEntrega(new TipoEntrega());
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
