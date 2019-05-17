package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private CervejaRepository cervejaRepository;

    @GetMapping("/Home")
    public ModelAndView home() {
        return new ModelAndView("/home");
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

}
