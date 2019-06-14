package br.com.wda.OpenBeerProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Wesley Santos_2
 */

@Controller
@RequestMapping("/erro")
public class ErroController {
    
    @GetMapping("/403")
    public String erroNaoPermitido() {
        return "erro403";
    }
    
}
