package br.com.wda.OpenBeerProject.Entity;

import org.springframework.stereotype.Component;

/**
 *
 * @author Wesley Santos_2
 */

@Component
public class DadosCliente {
    
    private Login login;
    private Endereco endereco;
    private Cliente cliente;

    public DadosCliente() {
    }

    public DadosCliente(Login login, Endereco endereco, Cliente cliente) {
        this.login = login;
        this.endereco = endereco;
        this.cliente = cliente;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }        
    
}
