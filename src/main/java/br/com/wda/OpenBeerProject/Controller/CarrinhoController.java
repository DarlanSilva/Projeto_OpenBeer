package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.ItensCarrinho;
import br.com.wda.OpenBeerProject.Entity.TipoEntrega;
import br.com.wda.OpenBeerProject.Repository.CarrinhoRepository;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import br.com.wda.OpenBeerProject.Repository.ItensCarrinhoRepository;
import br.com.wda.OpenBeerProject.Repository.TipoEntregaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 */

@Controller
@RequestMapping("/OpenBeer/Carrinho")
public class CarrinhoController {

    @Autowired
    private ItensCarrinhoRepository itensCarrinhoRepository;

    @Autowired
    private CervejaRepository cervejaRepository;
    
    private TipoEntregaRepository tipoEntrega;

    @GetMapping("{id}/AddCarrinho")
    public ModelAndView addcarrinho(@PathVariable("id") Integer id) {
        //Recebe o id da cerveja para adicionar no carrinho
        Optional<Cerveja> cerveja;
        if (id > 0 || id != null) {
            cerveja = cervejaRepository.findById(id);
        } else {
            return new ModelAndView("redirect:/OpenBeer/cerveja/Lista-de-Cervejas");
        }

        ItensCarrinho item = new ItensCarrinho();
        item.setCliente(1);
        item.setQuantidade(1);
        item.setDhInclusao(LocalDateTime.now());
        item.setVlTotal(cerveja.get().getValorCerveja());
        item.setCerveja(cerveja.get().getId());

        // SALVA O ITEM NO CARRINHO
        itensCarrinhoRepository.save(item);

        List<ItensCarrinho> carrinho = itensCarrinhoRepository.findAll();

        return new ModelAndView("carrinho")
                .addObject("carrinho", carrinho);
    }

    @GetMapping("/MeuCarrinho")
    public ModelAndView carrinho() {
        List<ItensCarrinho> carrinho = itensCarrinhoRepository.findAll();

        return new ModelAndView("carrinho")
                .addObject("carrinho", carrinho);
    }
    
    @GetMapping("/Compra")
    public ModelAndView compra() {
        List<ItensCarrinho> carrinho = itensCarrinhoRepository.findAll();

        return new ModelAndView("carrinho")
                .addObject("carrinho", carrinho);
    }
    
    @PostMapping("/{id}/remover")
    public ModelAndView removerCarrinho(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        
        itensCarrinhoRepository.deleteById(id);

       return new ModelAndView("redirect:/OpenBeer/Carrinho/MeuCarrinho");
    }
    //PROBLEMA
//    @ModelAttribute("tipoEntrega")
//    public List<TipoEntrega> getTipoEntrega() {
//
//        return tipoEntrega.findAll();
//    }

}
