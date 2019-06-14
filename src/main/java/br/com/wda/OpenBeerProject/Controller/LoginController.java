package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.CarrinhoCompras;
import br.com.wda.OpenBeerProject.Entity.Cliente;
import br.com.wda.OpenBeerProject.Entity.Endereco;
import br.com.wda.OpenBeerProject.Entity.Login;
import br.com.wda.OpenBeerProject.Entity.Permissao;
import br.com.wda.OpenBeerProject.Repository.ClienteRepository;
import br.com.wda.OpenBeerProject.Repository.EnderecoRepository;
import br.com.wda.OpenBeerProject.Repository.LoginRepository;
import br.com.wda.OpenBeerProject.Repository.PermissaoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 */
@Controller
@RequestMapping("/OpenBeer/Login")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PermissaoRepository permissaoRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private CarrinhoCompras carrinho;

    @Autowired
    private EnderecoRepository enderecoRepo;

    @GetMapping("/login")
    @PostMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("cliente/login-cadastro").addObject("login", new Login());
    }

    @GetMapping("/Sucess")
    public ModelAndView loginSucesso(HttpSession session) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<Cliente> cliente = clienteRepo.findByUser(username);

        // GUARDA O CLIENTE LOGADO NA SESSÃO
        if (cliente.isPresent() == true) {
            Optional<Login> login = loginRepository.findByClienteID(cliente.get().getId());

            if (login.isPresent() == true) {
                cliente.get().setLogin(login.get());
            }

            carrinho.setCliente(cliente.get());

            Optional<Endereco> endereco = enderecoRepo.findByClienteId(cliente.get().getId());

            if (endereco.isPresent() == true) {
                carrinho.setEndereco(endereco.get());
            }
            session.setAttribute("carrinhoCompras", carrinho);
        }

        return new ModelAndView("redirect:/OpenBeer/Home");
    }

    @GetMapping("/lista-de-login")
    public ModelAndView listarLogin() {
        List<Login> login = loginRepository.findAll();
        return new ModelAndView("cliente/login-lista").addObject("login", login);
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable("id") Integer id) {
        Optional<Login> listLogin = loginRepository.findById(id);
        Login login = listLogin.get();

        return new ModelAndView("cliente/login-cadastro").addObject("login", login);
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute("login") @Valid Login login, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("cliente/login-cadastro");
            mv.addObject(login);
            return mv;
        }
        Optional<Login> verificarLogin = loginRepository.findByEmail(login.getEmail());

        // CASO O USUARIO JÁ TENHA CADASTRADO O LOGIN POREM NÃO FINALIZAOU O CADASTRO ELE É REDIRECIONADO PARA CADASTRAR O DADOS PESSOAIS DIRETO
        if (verificarLogin.isPresent() == true) {

            Optional<Cliente> verificarCliente = clienteRepo.findByLogin(verificarLogin.get().getId());

            // CASO O EMAIL INFORMADO JA POSSUA UM CLIENTE VINVULADO A ELE REDIRECIONA DO USUARIO PARA TELA DE LOGIN COM MENSAGEM
            if (verificarCliente.isPresent() == true) {
                ModelAndView mv = new ModelAndView("cliente/login-cadastro");

                redirectAttributes.addFlashAttribute("mensagem", "O email já esta em uso.");

                return mv;
            }

            ModelAndView mvDadosPessoais = new ModelAndView("cliente/dados-pessoais");
            Cliente cliente = new Cliente();
            cliente.setLogin(verificarLogin.get());
            mvDadosPessoais.addObject("cliente", cliente);

            return mvDadosPessoais;
        }
        Optional<Permissao> permissaoCliente = permissaoRepo.findById(1);
        List<Permissao> permissao = new ArrayList<Permissao>();
        permissao.add(permissaoCliente.get());

        login.setDhInclusao(LocalDateTime.now());
        login.setInativo(0);
        login.setPermissaoAcesso(permissao);

        if (login.getId() != null) {
            login.setDhAlteracao(LocalDateTime.now());
        }
        // OBJETO INSTANCIADO PARA RECEBER O LOGIN SALVO JÁ COM O ID DO BANCO
        Login loginSalvo = new Login();

        // SALVA LOGIN NO BANCO
        loginSalvo = loginRepository.save(login);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Login " + login.getEmail() + "criado com sucesso");

        // REDIRECIONA O USUÁRIO PARA CADASTRAR OS DADOS PESSOAIS
        ModelAndView mvDadosPessoais = new ModelAndView("cliente/dados-pessoais");
        Cliente cliente = new Cliente();
        cliente.setLogin(loginSalvo);
        mvDadosPessoais.addObject("cliente", cliente);

        return mvDadosPessoais;
    }

}
