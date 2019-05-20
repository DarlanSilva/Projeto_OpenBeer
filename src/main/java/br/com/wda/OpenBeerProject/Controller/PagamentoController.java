package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

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
//    @GetMapping("/ConferirCompra")
//    public ModelAndView conferir() {
//
//    }
}
