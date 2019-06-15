package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.CarrinhoItem;
import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.Cupom;
import br.com.wda.OpenBeerProject.Entity.TipoEntrega;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import br.com.wda.OpenBeerProject.Repository.CupomRepository;
import br.com.wda.OpenBeerProject.Repository.PedidoItensRepository;
import br.com.wda.OpenBeerProject.Repository.TipoEntregaRepository;
import ch.qos.logback.core.CoreConstants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 */
@Controller
@RequestMapping("/OpenBeer/Carrinho")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoController {

    @Autowired
    private CervejaRepository cervejaRepository;

    @Autowired
    private TipoEntregaRepository tipoEntregaRepository;

    @Autowired
    private CarrinhoCompras carrinho;

    @Autowired
    private CupomRepository cupomRepo;

    @Autowired
    private TipoEntregaRepository tipoEntregaRepo;

    private CarrinhoItem criarItem(Integer cervejaID) {
        Optional<Cerveja> cerveja = cervejaRepository.findById(cervejaID);
        CarrinhoItem carrinhoItem = new CarrinhoItem(cerveja.get());

        return carrinhoItem;
    }

    @GetMapping("{id}/AddCarrinho")
    public ModelAndView addAarrinho(@PathVariable("id") Integer id) {

        ModelAndView mv = new ModelAndView("redirect:/OpenBeer/Carrinho");
        CarrinhoItem carrinhoItem = criarItem(id);
        carrinho.add(carrinhoItem);

        if (carrinho.getIdTipoEntrega() == null || carrinho.getIdTipoEntrega() == 0) {

            Optional<TipoEntrega> tipoEntrega = tipoEntregaRepo.findById(1);
            carrinho.setIdTipoEntrega(0);
            carrinho.setPrazoEntrega(tipoEntrega.get().getPrazoEntrega());
            carrinho.setValorEntrega(tipoEntrega.get().getValorEntrega());
            carrinho.setValorDesconto(BigDecimal.ZERO);

        }

        return mv;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView itensCarrinho() {

        ModelAndView mv = new ModelAndView("carrinho/carrinho");
        return mv;
    }

    @GetMapping("/Compra")
    public ModelAndView compra() {
        return new ModelAndView("carrinho/carrinho")
                .addObject("carrinho", carrinho);
    }

    @GetMapping("/{id}/remover")
    public ModelAndView removerCarrinho(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        carrinho.remover(id);

        return new ModelAndView("redirect:/OpenBeer/Carrinho");
    }

    @RequestMapping(value = "/VerificarCupom", method = RequestMethod.POST)
    public @ResponseBody
    Cupom cupomDesconto(@RequestBody Map<String, Object> corpo) {
        String cup[] = new String[2];
        String cupom = corpo.toString();
        cup = cupom.split("=");
        Optional<Cupom> copom = cupomRepo.findByCupom(cup[1].replaceAll("}", "").trim());

        if (copom.isPresent() == false) {
            return new Cupom();
        }
        if (copom.get().getDhValidade().isBefore(LocalDateTime.now())) {
            return new Cupom();
        }

        carrinho.setValorDesconto(copom.get().getValorCupom());
        copom.get().setValorTotal(carrinho.getTotalCompra());

        return copom.get();
    }

    @RequestMapping(value = "/VerificarTipoEntrega", method = RequestMethod.POST)
    public @ResponseBody
    TipoEntrega tipoEntrega(@RequestBody Map<String, Object> corpo) {
        String tipoEntregaID[] = new String[2];
        String tipo = corpo.toString();
        tipoEntregaID = tipo.split("=");

        Optional<TipoEntrega> tipoEntrega = tipoEntregaRepository.findById(Integer.parseInt(tipoEntregaID[1].replaceAll("}", "").trim()));

        if (tipoEntrega.isPresent() == false) {
            return new TipoEntrega();
        }

        carrinho.setPrazoEntrega(tipoEntrega.get().getPrazoEntrega());
        carrinho.setValorEntrega(tipoEntrega.get().getValorEntrega());
        carrinho.setIdTipoEntrega(tipoEntrega.get().getId());
        System.out.println(carrinho.getTotalCompra());
        tipoEntrega.get().setValorTotal(carrinho.getTotalCompra());

        return tipoEntrega.get();

    }

    @ModelAttribute("tipoEntrega")
    public List<TipoEntrega> getTipoEntrega() {

        return tipoEntregaRepository.findAll();
    }
}
