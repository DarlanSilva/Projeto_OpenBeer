package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Cerveja;
import br.com.wda.OpenBeerProject.Entity.FilterRel;
import br.com.wda.OpenBeerProject.Entity.Pedido;
import br.com.wda.OpenBeerProject.Entity.PedidoItens;
import br.com.wda.OpenBeerProject.Entity.StatusPedido;
import br.com.wda.OpenBeerProject.Entity.TipoCerveja;
import br.com.wda.OpenBeerProject.Infra.FileSaver;
import br.com.wda.OpenBeerProject.Repository.CervejaRepository;
import br.com.wda.OpenBeerProject.Repository.PedidoItensRepository;
import br.com.wda.OpenBeerProject.Repository.PedidoRepository;
import br.com.wda.OpenBeerProject.Repository.StatusPedidoRepository;
import br.com.wda.OpenBeerProject.Repository.TipoCervejaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Autowired
    private StatusPedidoRepository statusPedidoRepo;

    @Autowired
    private PedidoItensRepository pedidoItensRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private FilterRel filterRel;

    @GetMapping("/Consultar-Produtos")
    public ModelAndView manutencao() {

        List<Cerveja> cerveja = cervejaRepository.findAll();
        return new ModelAndView("backOffice/consultar-produto")
                .addObject("tableData", cerveja);
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

    @GetMapping("/{id}/remover")
    public ModelAndView remover(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Impossível deletar este produto, pois o mesmo possue vinculo com pedidos efetuados.");

        return new ModelAndView("redirect:/OpenBeer/BackOffice/Consultar-Produtos");

//        cervejaRepository.deleteById(id);
//
//        redirectAttributes.addFlashAttribute("mensagemSucesso",
//                "Cerveja ID " + id + " removido com sucesso");
//
//        return new ModelAndView("redirect:/OpenBeer/BackOffice/Consultar-Produtos");
    }

    @GetMapping("/Relatorio-Pedidos")
    @Cacheable(value = "relatorio-pedidos")
    public ModelAndView relatorioPedidos() {
        ModelAndView mv = new ModelAndView("backOffice/relatorio");

        List<Pedido> pedidos = pedidoRepo.findAllByDhInclusao();
        List<PedidoItens> itens = pedidoItensRepo.findAllByDhInclusao();
        mv.addObject("itens", itens);
        mv.addObject("pedido", pedidos);
        mv.addObject("filterRel", filterRel);

        return mv;
    }

    @RequestMapping(value = "/Relatorio-Pedidos", method = RequestMethod.POST)
    public ModelAndView relatorioPedidosBusca(@ModelAttribute("filterRel") FilterRel filterRel) {
        Date dtInicio = filterRel.getDtInicio();
        Date dtFinal = filterRel.getDtFinal();

        System.out.println(dtInicio);
        System.out.println(dtFinal);

        List<Pedido> listaPedidos;
        if (dtInicio != null && dtFinal != null) {
            listaPedidos = pedidoRepo.findAllByDhInclusao(dtInicio, dtFinal);
        } else if (dtInicio != null && dtFinal == null) {
            listaPedidos = pedidoRepo.findAllByDhInclusaoIni(dtInicio);
        } else if (dtInicio == null && dtFinal != null) {
            listaPedidos = pedidoRepo.findAllByDhInclusaoFin(dtFinal);
        } else {
            listaPedidos = (List<Pedido>) pedidoRepo.findAll();
        }

        List<PedidoItens> listaPedidosItens;
        if (dtInicio != null && dtFinal != null) {
            listaPedidosItens = pedidoItensRepo.findAllByDhInclusao(dtInicio, dtFinal);
        } else if (dtInicio != null && dtFinal == null) {
            listaPedidosItens = pedidoItensRepo.findAllByDhInclusaoIni(dtFinal);
        } else if (dtInicio == null && dtFinal != null) {
            listaPedidosItens = pedidoItensRepo.findAllByDhInclusaoFin(dtFinal);
        } else {
            listaPedidosItens = (List<PedidoItens>) pedidoItensRepo.findAll();
        }

        ModelAndView mv = new ModelAndView("backOffice/relatorio");

        mv.addObject("itens", listaPedidosItens);
        mv.addObject("pedido", listaPedidos);

        return mv;
    }

    @RequestMapping(value = "/Alterar-Status-Pedido", method = RequestMethod.POST)
    public @ResponseBody
    void alterarStatusPedido(@RequestBody Map<String, Object> corpo) {
        String json[] = new String[3];
        String pedido[] = new String[2];
        String filtro = corpo.toString();
        json = filtro.split("=");
        pedido = json[1].split(",");
        System.out.println(corpo);

        Optional<Pedido> alteraPedido = pedidoRepo.findById(Integer.parseInt(pedido[0].trim()));
        Optional<StatusPedido> statusPedido = statusPedidoRepo.findAllByStatus(json[2].replaceAll("}", "").trim());

        if (json[2].replaceAll("}", "").trim().toUpperCase().equals("PEDIDO ENTREGUE")) {
            alteraPedido.get().setDhEntregue(LocalDateTime.now());
        }

        if (json[2].replaceAll("}", "").trim().toUpperCase().equals("PEDIDO RECUSADO")) {
            alteraPedido.get().setDhPedidoCancelado(LocalDateTime.now());
        }

        alteraPedido.get().setStatus(statusPedido.get());

        pedidoRepo.save(alteraPedido.get());

        //return new ModelAndView("redirect:/OpenBeer/BackOffice/Relatorio-Pedidos");
    }

    @ModelAttribute("tipoCerveja")
    @Cacheable(value = "tipos-cervejas")
    public List<TipoCerveja> getTipoCerveja() {

        return tipoCervejaRepository.findAll();
    }

    @ModelAttribute("statusPedido")
    @Cacheable(value = "status-pedido")
    public List<StatusPedido> getStatusPedido() {

        return statusPedidoRepo.findAll();
    }

    @ModelAttribute("filterRel")
    public FilterRel getFilter() {
        return filterRel;
    }

}
