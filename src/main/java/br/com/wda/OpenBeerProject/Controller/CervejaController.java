package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.TipoCerveja;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import br.com.wda.OpenBeerProject.Repository.TipoCervejaRepository;
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
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
@Controller
@RequestMapping("/OpenBeer/cerveja")
public class CervejaController {

    @Autowired
    private CervejaRepository cervejaRepository;

    @Autowired
    private TipoCervejaRepository tipoCervejaRepository;
    
    @GetMapping("/BackOffice")
    public ModelAndView manutencao() {
        
        List<Cerveja> cerveja = cervejaRepository.findAll();
        return new ModelAndView("consultar-produto")
                .addObject("cerveja", cerveja);
    }

    @GetMapping("/Lista-de-Cervejas")
    public ModelAndView listagemProd() {
        List<Cerveja> cerveja = cervejaRepository.findAll();
        
        return new ModelAndView("produtos-lista").addObject("cerveja", cerveja);
    }

    @GetMapping("/novo")
    public ModelAndView adicionarNovo() {
        return new ModelAndView("cadastro-produto")
                .addObject("cerveja", new Cerveja());
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable("id") Integer id) {
        Optional<Cerveja> listProd = cervejaRepository.findById(id);
        Cerveja cerveja = listProd.get();
        getTipoCerveja();

        return new ModelAndView("cadastro-produto")
                .addObject("cerveja", cerveja);
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute("cerveja") Cerveja cerveja, RedirectAttributes redirectAttributes) {

        cerveja.setDhInclusao(LocalDateTime.now());
        cerveja.setInativo(0);

        //VERIFICA SE É UMA ALTERAÇÃO PARA SALVA A DATA HORA DA ALTERAÇÃO
        if (cerveja.getId() != null) {
            cerveja.setDhAlteracao(LocalDateTime.now());
        }

        cervejaRepository.save(cerveja);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Cerveja " + cerveja.getNome() + " salvo com sucesso");

        return new ModelAndView("redirect:/OpenBeer/cerveja/BackOffice");
    }

    @PostMapping("/{id}/remover")
    public ModelAndView remover(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        cervejaRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Cerveja ID " + id + " removido com sucesso");

       return new ModelAndView("redirect:/OpenBeer/cerveja/BackOffice");
    }

    @ModelAttribute("tipoCerveja")
    public List<TipoCerveja> getTipoCerveja() {

        return tipoCervejaRepository.findAll();
    }
}
