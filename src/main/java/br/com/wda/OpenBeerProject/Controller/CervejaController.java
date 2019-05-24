package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.TipoCerveja;
import br.com.wda.OpenBeerProject.Infra.FileSaver;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import br.com.wda.OpenBeerProject.Repository.TipoCervejaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
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
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
//@MultipartConfig(maxFileSize = Long.MAX_VALUE)
public class CervejaController {

    @Autowired
    private CervejaRepository cervejaRepository;

    @Autowired
    private TipoCervejaRepository tipoCervejaRepository;

    @Autowired
    private CarrinhoCompras carrinho;

    @Autowired
    private FileSaver fileSaver;

    @GetMapping("/BackOffice")
    public ModelAndView manutencao() {

        List<Cerveja> cerveja = cervejaRepository.findAll();
        return new ModelAndView("consultar-produto")
                .addObject("cerveja", cerveja);
    }

    @GetMapping("/Lista-de-Cervejas")
    public ModelAndView listagemProd() {
        List<Cerveja> cerveja = cervejaRepository.findAll();
        ModelAndView mv = new ModelAndView("produtos-lista");
        mv.addObject("cerveja", cerveja);

        return mv;
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
    public ModelAndView salvar(MultipartFile imagemCerveja, @ModelAttribute("cerveja") 
                        @Valid Cerveja cerveja, RedirectAttributes redirectAttributes, BindingResult result ) {
        
        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("cadastro-produto");
            mv.addObject("cerveja", cerveja);
            
            return mv;
        }
        
        cerveja.setDhInclusao(LocalDateTime.now());
        cerveja.setInativo(0);

        //VERIFICA SE É UMA ALTERAÇÃO PARA SALVA A DATA HORA DA ALTERAÇÃO
        if (cerveja.getId() != null) {
            cerveja.setDhAlteracao(LocalDateTime.now());
        }
            
        String path = fileSaver.write("c://uploads//", imagemCerveja);
        cerveja.setImagem(path);

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

    @GetMapping("{id}/detalhe")
    public ModelAndView detalhe(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("produto");

        Optional<Cerveja> cerveja = cervejaRepository.findById(id);

        if (cerveja.get().getId() == null) {
            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Cerveja indisponível.");

            return listagemProd();
        }
        mv.addObject("cerveja", cerveja.get());

        return mv;

    }

    @ModelAttribute("tipoCerveja")
    public List<TipoCerveja> getTipoCerveja() {

        return tipoCervejaRepository.findAll();
    }
}
