package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.Cliente;
import br.com.wda.OpenBeerProject.Entity.SenderMail;
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
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class CervejaController {

    @Autowired
    private CervejaRepository cervejaRepository;

    @Autowired
    private TipoCervejaRepository tipoCervejaRepository;
    
    @Autowired
    private MailSender sender;

    @Autowired
    private CarrinhoCompras carrinho;

    @Autowired
    private FileSaver fileSaver;

    @GetMapping("/BackOffice")
    public ModelAndView manutencao() {

        List<Cerveja> cerveja = cervejaRepository.findAll();
        return new ModelAndView("backOffice/consultar-produto")
                .addObject("cerveja", cerveja);
    }
    
    @GetMapping("/Lista-de-Cervejas")
    @Cacheable(value = "lista-cervejas")
    public ModelAndView listagemProd() {
        
        List<Cerveja> cerveja= cervejaRepository.findAll();        
        ModelAndView mv = new ModelAndView("produto/produtos-lista");
        mv.addObject("cerveja", cerveja);

        return mv;
    }

    @GetMapping("/{busca}/Lista-de-Cervejas")
    @Cacheable(value = "lista-cervejas")
    public ModelAndView buscaListagemProd(@PathVariable("busca") String busca) {
        List<Cerveja> cerveja;
        
        if (busca == null || busca.trim().isEmpty()) {
            cerveja = cervejaRepository.findAll();
        } else {
             cerveja = cervejaRepository.findByNome(busca);
        }
        
        ModelAndView mv = new ModelAndView("produto/produtos-lista");
        mv.addObject("cerveja", cerveja);

        return mv;
    }

    @GetMapping("/{tipoCerveja}/Lista-de-Cervejas-por-Tipo")
    @Cacheable(value = "lista-cervejas")
    public ModelAndView listagemProdPorTipo(@PathVariable("tipoCerveja") Integer tipoCerveja) {
        List<Cerveja> cerveja = cervejaRepository.findByTipoCerveja(tipoCerveja);
        ModelAndView mv = new ModelAndView("produto/produtos-lista");
        mv.addObject("cerveja", cerveja);

        return mv;
    }

    @GetMapping("/{ordemNome}/Lista-de-Cervejas-ordem-Nome")
    @Cacheable(value = "lista-cervejas")
    public ModelAndView listagemProdOrderByNome(@PathVariable("ordemNome") Integer ordemNome) {
        List<Cerveja> cerveja;

        if (ordemNome == 1) {
            cerveja = cervejaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        } else {
            cerveja = cervejaRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
        }

        ModelAndView mv = new ModelAndView("produto/produtos-lista");
        mv.addObject("cerveja", cerveja);

        return mv;
    }

    @GetMapping("/{ordemPreco}/Lista-de-Cervejas-ordem-Preco")
    @Cacheable(value = "lista-cervejas")
    public ModelAndView listagemProdOrderByPreco(@PathVariable("ordemPreco") Integer ordemPreco) {
        List<Cerveja> cerveja;

        if (ordemPreco == 1) {
            cerveja = cervejaRepository.findAll(Sort.by(Sort.Direction.ASC, "valorCerveja"));
        } else {
            cerveja = cervejaRepository.findAll(Sort.by(Sort.Direction.DESC, "valorCerveja"));
        }

        ModelAndView mv = new ModelAndView("produto/produtos-lista");
        mv.addObject("cerveja", cerveja);

        return mv;
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
    @CacheEvict(value ="lista-cervejas", allEntries=true)
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
        
        ModelAndView mv = new ModelAndView("produto/produto");

        Optional<Cerveja> cerveja = cervejaRepository.findById(id);

        if (cerveja.get().getId() == null) {
            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Cerveja indisponível.");

            return listagemProd();
        }
        
        // VERIFICA SE ESTA EM FALTA NO ESTOQUE SE SIM REDIRECIONA PARA A PAGINA DE AVISE-ME.
        if(cerveja.get().getQuantidade() == 0){
           ModelAndView mvIndisponivel = new ModelAndView("produto/produto-indisponivel");
           
           mvIndisponivel.addObject("cerveja", cerveja.get());
           mvIndisponivel.addObject("email", new SenderMail());
           
           return mvIndisponivel;
        }
        
        mv.addObject("cerveja", cerveja.get());

        return mv;

    }
    
    @PostMapping("/Avise-me")
    public ModelAndView produtoIndisponivel(@ModelAttribute("email") @Valid SenderMail mail, BindingResult result ,@ModelAttribute("cerveja") Cerveja cerveja){
        
        if (result.hasErrors()){
            ModelAndView mv = new ModelAndView("produto/produto-indisponivel");
            mv.addObject("cerveja", cerveja);
            
            return mv;
            
        }
        aviseMe(mail.getEmail(), mail.getNome());
        
        return new ModelAndView("redirect:/OpenBeer/cerveja/Lista-de-Cervejas");
    }

    @ModelAttribute("tipoCerveja")
    @Cacheable(value = "tipos-cervejas")
    public List<TipoCerveja> getTipoCerveja() {

        return tipoCervejaRepository.findAll();
    }
    
    private void aviseMe(String email, String nome){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject("OpenBeer - O melhor da cerveja esta aqui!!!");
        mail.setTo(email);
        mail.setText("Olá " + nome + " pode deixar que assim que o nosso estoque for reposto você será o primeiro a ser informado.");
        mail.setFrom("OpenBeer.com.br");
        
        sender.send(mail);
    }
}
