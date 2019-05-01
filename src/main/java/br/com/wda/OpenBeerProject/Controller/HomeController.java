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
    public ModelAndView adicionarNovo() {
        return new ModelAndView("/index");
    }
    
}
