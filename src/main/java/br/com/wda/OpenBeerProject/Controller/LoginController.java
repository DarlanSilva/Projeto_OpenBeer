/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wda.OpenBeerProject.Controller;

import br.com.wda.OpenBeerProject.Entity.Login;
import br.com.wda.OpenBeerProject.Repository.LoginRepository;
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
 * @author Wesley Moura
 * @author Alison Souza
 */
@Controller
@RequestMapping("/OpenBeer/Login")
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;
    
//    @RequestMapping(value = "/logar", method = RequestMethod.POST)
//    public String mostrarFormLogin() {
//        return "login-cadastro";
//    }    

    @GetMapping("/HomeLogin")
    @PostMapping("/HomeLogin")
//    @RequestMapping(value = "/HomeLogin", method = RequestMethod.POST)
    public ModelAndView homeLogin() {
        return new ModelAndView("/login-cadastro").addObject("login", new Login());
    }

    @GetMapping("/lista-de-login")
    public ModelAndView listarLogin() {
        List<Login> login = loginRepository.findAll();
        return new ModelAndView("login-lista").addObject("login", login);
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Login> listLogin = loginRepository.findById(id);
        Login login = listLogin.get();

        return new ModelAndView("login-cadastro").addObject("login", login);
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute("login") @Valid Login login, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("login-cadastro");
            mv.addObject(login);
            return mv;
        }
//        Permissao permissaoAcesso = null;
//        permissaoAcesso.getId();

        login.setDhInclusao(LocalDateTime.now());
        login.setInativo(0);
        login.setPermissaoAcesso(1);

        if (login.getId() != null) {
            login.setDhAlteracao(LocalDateTime.now());
        }

        loginRepository.save(login);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Login " + login.getEmail() + "criado com sucesso");

        return new ModelAndView("redirect:/OpenBeer/Home");
    }

}
