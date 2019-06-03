package br.com.wda.OpenBeerProject.Entity;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author Darlan Silva
 */

public class SenderMail {
    
    @NotBlank(message = "O nome deve ser informado.")
    private String nome;
    
    @NotBlank(message = "O email deve ser informado.")
    private String email;
    
    private String assunto;
    private String mensagem;

    public SenderMail() {
    }
    
    public SenderMail(String nome, String email, String assunto, String mensagem) {
        this.nome = nome;
        this.email = email;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
    
    
}
