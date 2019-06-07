package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.SenderMail;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/OpenBeer")
public class HomeController {

    @Autowired
    private CervejaRepository cervejaRepository;

    @Autowired
    private MailSender sender;

    @GetMapping("/Home")
    @Cacheable(value = "produtos")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home");
        List<Cerveja> cerveja = cervejaRepository.findAllByDestaque(true);
        mv.addObject("cerveja", cerveja);
        mv.addObject("email", new SenderMail());

        return mv;
    }

    @GetMapping("/Detalhe")
    public ModelAndView detalhe() {
        return new ModelAndView("/produto");
    }

    @PostMapping("/Home/Contato")
    public ModelAndView contato(@ModelAttribute("email") @Valid SenderMail mail, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("redirect:/OpenBeer/Home#contato");
            mv.addObject("email", mail);

            return mv;

        }
        // ENVIA EMAIL DE CONTATO
        contatoMail(mail);
        
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Email enviado com sucesso. Obrigado pelo contato com a OpenBeer.");
        
        return new ModelAndView("redirect:/OpenBeer/Home#contato");
    }

    private void contatoMail(SenderMail email) {
        SimpleMailMessage contato = new SimpleMailMessage();
        
        contato.setSubject(email.getAssunto());
        contato.setTo("darlan.rs@hotmail.com");
        contato.setText("Nome do Cliente: " + email.getNome() + "\n" +
                        "Email para contato: " + email.getEmail() + "\n" +
                        "Mensagem: " + email.getMensagem());
        contato.setFrom("OpenBeer.com.br");

        sender.send(contato);
    }

}
