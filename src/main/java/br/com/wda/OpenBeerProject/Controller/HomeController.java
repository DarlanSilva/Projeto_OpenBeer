package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value="produtos")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home");
        List<Cerveja> cerveja = cervejaRepository.findAllByDestaque(true);
        mv.addObject("cerveja", cerveja);
      
        return mv;
    }

    @GetMapping("/Detalhe")
    public ModelAndView detalhe() {
        return new ModelAndView("/produto");
    }

}
