package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.Cliente;
import br.com.wda.OpenBeerProject.Entity.DadosCliente;
import br.com.wda.OpenBeerProject.Entity.Endereco;
import br.com.wda.OpenBeerProject.Entity.Login;
import br.com.wda.OpenBeerProject.Entity.Pedido;
import br.com.wda.OpenBeerProject.Entity.PedidoItens;
import br.com.wda.OpenBeerProject.Entity.Permissao;
import br.com.wda.OpenBeerProject.Repository.ClienteRepository;
import br.com.wda.OpenBeerProject.Repository.EnderecoRepository;
import br.com.wda.OpenBeerProject.Repository.LoginRepository;
import br.com.wda.OpenBeerProject.Repository.PedidoItensRepository;
import br.com.wda.OpenBeerProject.Repository.PedidoRepository;
import br.com.wda.OpenBeerProject.Repository.PermissaoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 */
@Controller
@RequestMapping("/OpenBeer/Cliente")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private EnderecoRepository enderecoRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private PedidoItensRepository pedidoItensRepo;

    @Autowired
    private CarrinhoCompras carrinho;

    @Autowired
    private DadosCliente dadosCliente;

    @GetMapping("/Cadastro-Login")
    public ModelAndView cadatroLogin() {
        ModelAndView mv = new ModelAndView("cliente/login-cadastro");
        mv.addObject("login", new Login());

        return mv;
    }

    @GetMapping("/MeusPedidos")
    @Cacheable(value = "meus-pedidos")
    public ModelAndView meusPedidos() {
        ModelAndView mv = new ModelAndView("cliente/meus-pedidos");

        if (carrinho.getCliente().getId() != 0 || carrinho.getCliente().getId() != null) {
            List<Pedido> pedido = pedidoRepo.findAllByClienteID(carrinho.getCliente().getId());
            List<PedidoItens> itens = pedidoItensRepo.findAllByClienteID(carrinho.getCliente().getId());

            mv.addObject("pedido", pedido);
            mv.addObject("itens", itens);
        }

        return mv;
    }

    @GetMapping("/EditarCadastro")
    public ModelAndView editarCadastro() {

        ModelAndView mv = new ModelAndView("cliente/editar-cadastro");

        dadosCliente.setLogin(carrinho.getCliente().getLogin());
        dadosCliente.setCliente(carrinho.getCliente());
        dadosCliente.setEndereco(carrinho.getEndereco());

        mv.addObject("dadosCliente", dadosCliente);

        return mv;

    }

    @GetMapping("/Logout")
    public ModelAndView logout() {
        ModelAndView mv = new ModelAndView("home");

        return mv;
    }

    @PostMapping("/Atualizar-Dados-Cliente")
    public ModelAndView atualizarCliente(@ModelAttribute("dadosCliente") @Valid DadosCliente dadosCliente, BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("cliente/editar-cadastro");
            mv.addObject("dadosCliente", dadosCliente);

            return mv;
        }

        dadosCliente.getCliente().setDhAlteracao(LocalDateTime.now());
        dadosCliente.getEndereco().setDhAlteracao(LocalDateTime.now());
        dadosCliente.getLogin().setDhAlteracao(LocalDateTime.now());
        dadosCliente.getCliente().setLogin(carrinho.getCliente().getLogin());
        dadosCliente.getEndereco().setIdCliente(carrinho.getEndereco().getIdCliente());

        clienteRepository.save(dadosCliente.getCliente());
        enderecoRepo.save(dadosCliente.getEndereco());
        loginRepo.save(dadosCliente.getLogin());

        //Atualizar os dados alterados na sessão
        carrinho.setCliente(dadosCliente.getCliente());
        carrinho.setEndereco(dadosCliente.getEndereco());

        return new ModelAndView("redirect:/OpenBeer/Home");
    }

    @GetMapping("/lista-de-cliente")
    public ModelAndView listarCliente() {
        List<Cliente> cliente = clienteRepository.findAll();
        return new ModelAndView("cliente/cliente-lista").addObject("cliente", cliente);
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable("id") Integer id) {
        Optional<Cliente> listCliente = clienteRepository.findById(id);
        Cliente cliente = listCliente.get();
        return new ModelAndView("cliente/dados-pessoais").addObject("cliente", cliente);
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("cliente/dados-pessoais");
            mv.addObject(cliente);
            return mv;
        }

        Optional<Cliente> verificarCliente = clienteRepository.findByLogin(cliente.getLogin().getId());

        // CASO O USUÁRIO JÁ TENHA CADASTRADO OS DADOS PESSOAIS PORÉM NÃO FINALIZAOU O CADASTRO ELE É REDIRECIONADO PARA O CADASTRO DO EMAIL
        if (verificarCliente.isPresent() == true) {
            ModelAndView mvDadosEndereco = new ModelAndView("cliente/dados-endereco");
            Endereco endereco = new Endereco();
            endereco.setIdCliente(verificarCliente.get());
            mvDadosEndereco.addObject("endereco", endereco);

            return mvDadosEndereco;
        }

        cliente.setDhInclusao(LocalDateTime.now());
        cliente.setInativo(0);

        if (cliente.getId() != null) {
            cliente.setDhAlteracao(LocalDateTime.now());
        }

        // OBJETO INSTANCIADO PARA ARMAZENAR O CLIENTE SALVO NO BANCO JÁ COM O ID
        Cliente clienteSalvo = new Cliente();
        clienteSalvo = clienteRepository.save(cliente);

        // REDIRECIONA O USUÁRIO PARA CADASTRAR O ENDEREÇO
        ModelAndView mvDadosEndereco = new ModelAndView("cliente/dados-endereco");
        Endereco endereco = new Endereco();
        endereco.setIdCliente(clienteSalvo);
        mvDadosEndereco.addObject("endereco", endereco);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Dados cadastrados com sucesso");

        return mvDadosEndereco;
    }

}
