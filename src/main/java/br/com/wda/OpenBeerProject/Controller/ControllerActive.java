package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.Permissao;
import br.com.wda.OpenBeerProject.Repository.PermissaoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
    
    @ModelAttribute("carrinhoCompras")
    public CarrinhoCompras getItens (){
        return carrinho;
    }
    
    @ModelAttribute("quantidadeCarrinho")
    public int getQuantidadeCarrinho() {
        return carrinho.getQuantidade();
    }
    
}
