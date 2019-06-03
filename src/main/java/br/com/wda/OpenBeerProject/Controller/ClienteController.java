package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Cliente;
import br.com.wda.OpenBeerProject.Entity.Login;
import br.com.wda.OpenBeerProject.Repository.ClienteRepository;
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
    public ModelAndView salvar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("cliente/dados-pessoais");
            mv.addObject(cliente);
            return mv;
        }

        cliente.setDhInclusao(LocalDateTime.now());
        cliente.setInativo(0);
        cliente.setLogin(1);

        if (cliente.getId() != null) {
            cliente.setDhAlteracao(LocalDateTime.now());
        }

        clienteRepository.save(cliente);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Dados cadastrados com sucesso");
        return new ModelAndView("redirect:/OpenBeer/Endereco/Novo-Endereco");
    }

}
