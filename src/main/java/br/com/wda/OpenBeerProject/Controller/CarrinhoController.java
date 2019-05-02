package br.com.wda.OpenBeerProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Darlan Silva
 */

@Controller
@RequestMapping("/OpenBeer")
public class CarrinhoController {
    
    @GetMapping("/Carrinho")
    public ModelAndView carrinho() {
        return new ModelAndView("/carrinho");
    }
    
}
