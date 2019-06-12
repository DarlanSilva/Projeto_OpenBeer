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
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/Lista-de-Cervejas")
    @Cacheable(value = "lista-cervejas")
    public ModelAndView listagemProd() {

        List<Cerveja> cerveja = cervejaRepository.findAll();
        ModelAndView mv = new ModelAndView("produto/produtos-lista");
        mv.addObject("cerveja", cerveja);

        return mv;
    }

    @RequestMapping(value = "/Lista-de-Cervejas-por-Tipo", method = RequestMethod.POST)
    @Cacheable(value = "lista-cervejas")
    public @ResponseBody
    List<Cerveja> tipoEntrega(@RequestBody Map<String, Object> corpo) {
        String tipoCerveja[] = new String[2];
        String tipo = corpo.toString();
        tipoCerveja = tipo.split("=");

        List<Cerveja> listaCervejas;

        if (Integer.parseInt(tipoCerveja[1].replaceAll("}", "").trim()) == 0) {
            listaCervejas = cervejaRepository.findAll();
        } else {
            listaCervejas = cervejaRepository.findByTipoCerveja(Integer.parseInt(tipoCerveja[1].replaceAll("}", "").trim()));
        }

        return listaCervejas;

    }

    @RequestMapping(value = "/Lista-de-Cervejas-busca", method = RequestMethod.POST)
    @Cacheable(value = "lista-cervejas")
    public @ResponseBody
    List<Cerveja> buscaListagemProd(@RequestBody Map<String, Object> corpo) {
        String buscaCerveja[] = new String[2];
        String busca = corpo.toString();
        buscaCerveja = busca.split("=");

        List<Cerveja> cerveja;

        if (buscaCerveja[1].replaceAll("}", "").trim() == null || buscaCerveja[1].replaceAll("}", "").trim().isEmpty()) {
            cerveja = cervejaRepository.findAll();
        } else {
            cerveja = cervejaRepository.findByNome("%"+buscaCerveja[1].replaceAll("}", "").trim()+"%");
        }

        return cerveja;
    }

    @RequestMapping(value = "/Lista-de-Cervejas-ordem-Nome", method = RequestMethod.POST)
    @Cacheable(value = "lista-cervejas")
    public @ResponseBody
    List<Cerveja> listagemProdOrderByNome(@RequestBody Map<String, Object> corpo) {
        String ordemNome[] = new String[2];
        String ordem = corpo.toString();
        ordemNome = ordem.split("=");

        List<Cerveja> cerveja;

        if (Integer.parseInt(ordemNome[1].replaceAll("}", "").trim()) == 1) {
            cerveja = cervejaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        } else {
            cerveja = cervejaRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
        }

        return cerveja;
    }

    @RequestMapping(value = "/Lista-de-Cervejas-ordem-Preco", method = RequestMethod.POST)
    @Cacheable(value = "lista-cervejas")
    public @ResponseBody
    List<Cerveja> listagemProdOrderByPreco(@RequestBody Map<String, Object> corpo) {
        String ordemPreco[] = new String[2];
        String ordem = corpo.toString();
        ordemPreco = ordem.split("=");

        List<Cerveja> cerveja;

        if (Integer.parseInt(ordemPreco[1].replaceAll("}", "").trim()) == 1) {
            cerveja = cervejaRepository.findAll(Sort.by(Sort.Direction.ASC, "valorCerveja"));
        } else {
            cerveja = cervejaRepository.findAll(Sort.by(Sort.Direction.DESC, "valorCerveja"));
        }

        return cerveja;
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
        if (cerveja.get().getQuantidade() == 0) {
            ModelAndView mvIndisponivel = new ModelAndView("produto/produto-indisponivel");

            mvIndisponivel.addObject("cerveja", cerveja.get());
            mvIndisponivel.addObject("email", new SenderMail());

            return mvIndisponivel;
        }

        mv.addObject("cerveja", cerveja.get());

        return mv;

    }

    @PostMapping("/Avise-me")
    public ModelAndView produtoIndisponivel(@ModelAttribute("email") @Valid SenderMail mail, BindingResult result, @ModelAttribute("cerveja") Cerveja cerveja) {

        if (result.hasErrors()) {
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

    private void aviseMe(String email, String nome) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject("OpenBeer - O melhor da cerveja esta aqui!!!");
        mail.setTo(email);
        mail.setText("Olá " + nome + " pode deixar que assim que o nosso estoque for reposto você será o primeiro a ser informado.");
        mail.setFrom("OpenBeer.com.br");

        sender.send(mail);
    }
}
