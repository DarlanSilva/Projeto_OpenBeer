package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 */
@Controller
@RequestMapping("/OpenBeer/Pagamento")
public class PagamentoController {

    @Autowired
    private CarrinhoCompras carrinho;

    private RestTemplate restTemplate;

//    @PostMapping("/Finalizar")
//    public ModelAndView finalizar() {
//
//    }
//
    @GetMapping("/ConferirCompra")
    public ModelAndView conferir() {
        ModelAndView mv = new ModelAndView("confirmacao");
        // O objeto usado esta como atributo para todas as views
        return mv;
    }

    @GetMapping("/{id}/remover")
    public ModelAndView removerCarrinho(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        carrinho.remover(id);

        return new ModelAndView("redirect:/OpenBeer/Pagamento/ConferirCompra");
    }
}
