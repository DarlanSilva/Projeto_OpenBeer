package br.com.wda.OpenBeerProject.Controller;

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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 */
@Controller
@RequestMapping("/OpenBeer/BackOffice")
public class BackOfficeController {

    @Autowired
    private CervejaRepository cervejaRepository;

    @Autowired
    private TipoCervejaRepository tipoCervejaRepository;
    
    @Autowired
    private FileSaver fileSaver;
    

    @GetMapping("/Consultar-Produtos")
    public ModelAndView manutencao() {

        List<Cerveja> cerveja = cervejaRepository.findAll();
        return new ModelAndView("backOffice/consultar-produto")
                .addObject("cerveja", cerveja);
    }

    @GetMapping("/novo")
    public ModelAndView adicionarNovo() {
        return new ModelAndView("backOffice/cadastro-produto")
                .addObject("cerveja", new Cerveja());
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable("id") Integer id) {
        Optional<Cerveja> listProd = cervejaRepository.findById(id);
        Cerveja cerveja = listProd.get();
        getTipoCerveja();

        return new ModelAndView("backOffice/cadastro-produto")
                .addObject("cerveja", cerveja);
    }

    @PostMapping("/salvar")
    @CacheEvict(value = "lista-cervejas", allEntries = true)
    public ModelAndView salvar(MultipartFile imagemCerveja, @ModelAttribute("cerveja")
            @Valid Cerveja cerveja, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("backOffice/cadastro-produto");
            mv.addObject("cerveja", cerveja);

            return mv;
        }

        cerveja.setDhInclusao(LocalDateTime.now());
        cerveja.setInativo(0);

        //VERIFICA SE É UMA ALTERAÇÃO PARA SALVA A DATA HORA DA ALTERAÇÃO
        if (cerveja.getId() != null) {
            cerveja.setDhAlteracao(LocalDateTime.now());
        }

        String path = fileSaver.write("/product-picture", imagemCerveja);
        cerveja.setImagem(path);

        cervejaRepository.save(cerveja);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Cerveja " + cerveja.getNome() + " salvo com sucesso");

        return new ModelAndView("redirect:/OpenBeer/BackOffice/Consultar-Produtos");
    }

    @PostMapping("/{id}/remover")
    public ModelAndView remover(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        cervejaRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Cerveja ID " + id + " removido com sucesso");

        return new ModelAndView("redirect:/OpenBeer/BackOffice/Consultar-Produtos");
    }

    @ModelAttribute("tipoCerveja")
    @Cacheable(value = "tipos-cervejas")
    public List<TipoCerveja> getTipoCerveja() {

        return tipoCervejaRepository.findAll();
    }

}
