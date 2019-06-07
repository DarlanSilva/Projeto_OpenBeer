package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.CarrinhoItem;
import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.PedidoItens;
import br.com.wda.OpenBeerProject.Entity.TipoEntrega;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import br.com.wda.OpenBeerProject.Repository.PedidoItensRepository;
import br.com.wda.OpenBeerProject.Repository.TipoEntregaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 */
@Controller
@RequestMapping("/OpenBeer/Carrinho")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoController {

    @Autowired
    private PedidoItensRepository carrinhoItensRepository;

    @Autowired
    private CervejaRepository cervejaRepository;

    @Autowired
    private TipoEntregaRepository tipoEntregaRepository;

    @Autowired
    private CarrinhoCompras carrinho;

    private CarrinhoItem criarItem(Integer cervejaID) {
        Optional<Cerveja> cerveja = cervejaRepository.findById(cervejaID);
        CarrinhoItem carrinhoItem = new CarrinhoItem(cerveja.get());

        return carrinhoItem;
    }

    @GetMapping("{id}/AddCarrinho")
    public ModelAndView addAarrinho(@PathVariable("id") Integer id) {

        ModelAndView mv = new ModelAndView("redirect:/OpenBeer/Carrinho");
        CarrinhoItem carrinhoItem = criarItem(id);
        carrinho.add(carrinhoItem);
        return mv;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView itensCarrinho() {

        ModelAndView mv = new ModelAndView("carrinho/carrinho");
        return mv;
    }

    @GetMapping("/Compra")
    public ModelAndView compra() {
        List<PedidoItens> carrinho = carrinhoItensRepository.findAll();

        return new ModelAndView("carrinho/carrinho")
                .addObject("carrinho", carrinho);
    }

    @GetMapping("/{id}/remover")
    public ModelAndView removerCarrinho(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        
        carrinho.remover(id);

        return new ModelAndView("redirect:/OpenBeer/Carrinho");
    }
    
    @ModelAttribute("tipoEntrega")
    public List<TipoEntrega> getTipoEntrega() {

        return tipoEntregaRepository.findAll();
    }
}
