package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Cliente;
import br.com.wda.OpenBeerProject.Entity.Endereco;
import br.com.wda.OpenBeerProject.Entity.Login;
import br.com.wda.OpenBeerProject.Entity.Permissao;
import br.com.wda.OpenBeerProject.Repository.ClienteRepository;
import br.com.wda.OpenBeerProject.Repository.PermissaoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
 */
@Controller
@RequestMapping("/OpenBeer/Cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/Cadastro-Login")
    public ModelAndView cadatroLogin() {
        ModelAndView mv = new ModelAndView("cliente/login-cadastro");
        mv.addObject("login", new Login());

        return mv;
    }

    @GetMapping("/Login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("cliente/login-cadastro");
        mv.addObject("login", new Login());

        return mv;
    }

    @GetMapping("/MeusPedidos")
    public ModelAndView meusPedidos() {
        ModelAndView mv = new ModelAndView("cliente/meus-pedidos");

        return mv;
    }

    @GetMapping("/EditarCadastro")
    public ModelAndView editarCadastro() {
        ModelAndView mv = new ModelAndView("cliente/editar-cadastro");

        return mv;
    }

    @GetMapping("/Logout")
    public ModelAndView logout() {
        ModelAndView mv = new ModelAndView("home");

        return mv;
    }

    @GetMapping("/Cadastrar-Novo-Endereco")
    public ModelAndView novoEndereco() {
        ModelAndView mv = new ModelAndView("cliente/cadastro-endereco");

        return mv;
    }

    @GetMapping("/Dados-Pessoais")
    public ModelAndView dadosPessoais() {
        return new ModelAndView("cliente/dados-pessoais").addObject("cliente", new Cliente());
    }

    @GetMapping("/lista-de-cliente")
    public ModelAndView listarCliente() {
        List<Cliente> cliente = clienteRepository.findAll();
        return new ModelAndView("cliente/cliente-lista").addObject("cliente", cliente);
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cliente> listCliente = clienteRepository.findById(id);
        Cliente cliente = listCliente.get();
        return new ModelAndView("cliente/dados-pessoais").addObject("cliente", cliente);
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result, @ModelAttribute("login") Login login, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("cliente/dados-pessoais");
            mv.addObject(cliente);
            return mv;
        }

        Optional<Cliente> verificar = clienteRepository.findByLogin(login.getId());

        // Caso o usuário já tenha cadastrado os dados pessoais porém não finalizaou o cadastro ele é redirecionado para direto
        if (verificar.isPresent() == true) {
            ModelAndView mvDadosEndereco = new ModelAndView("cliente/dados-endereco");
            mvDadosEndereco.addObject("endereco", new Endereco());
            mvDadosEndereco.addObject("cliente", verificar);

            return mvDadosEndereco;
        }

        cliente.setDhInclusao(LocalDateTime.now());
        cliente.setInativo(0);
        cliente.setLogin(login);

        if (cliente.getId() != null) {
            cliente.setDhAlteracao(LocalDateTime.now());
        }
        clienteRepository.save(cliente);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Dados cadastrados com sucesso");

        ModelAndView mvDadosEndereco = new ModelAndView("cliente/dados-endereco");
        mvDadosEndereco.addObject("endereco", new Endereco());
        mvDadosEndereco.addObject("cliente", verificar);

        return mvDadosEndereco;
    }

}
