package br.com.wda.OpenBeerProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
@Controller
@RequestMapping("/OpenBeer")
public class HomeController {

    @GetMapping("/Home")
    public ModelAndView home() {
        return new ModelAndView("/home");
    }

    @GetMapping("/Cervejas")
    public ModelAndView lista() {
        return new ModelAndView("/produtos-lista");
    }

    @GetMapping("/Contato")
    public ModelAndView contado() {
        return new ModelAndView("/home");
    }

    @GetMapping("/Sobre")
    public ModelAndView sobre() {
        return new ModelAndView("/home");
    }
    
    @GetMapping("/Produto")
    public ModelAndView detalhe() {
        return new ModelAndView("/produto");
    }
    
    @GetMapping("/Manutencao")
    public ModelAndView manutencao() {
        return new ModelAndView("/consultar-produto");
    }

}
