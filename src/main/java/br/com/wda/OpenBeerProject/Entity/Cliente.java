/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wda.OpenBeerProject.Entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 */
@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CPF")
    @NotBlank(message = "CAMPO CPF OBRIGATÓRIO")
    private String cpf;

    @Column(name = "NOME")
    @NotBlank(message = "CAMPO NOME COMPLETO OBRIGATÓRIO")
    private String nomeCompleto;

    @NotBlank(message = "CAMPO OBRIGATÓRIO")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "DT_NASCIMENTO")
    private String dtNascimento;

    @NotBlank(message = "CAMPO OBRIGATÓRIO")
    @Column(name = "TG_SEXO")
    private String sexo;

    @NotBlank(message = "CAMPO OBRIGATÓRIO")
    @Column(name = "TELEFONE")
    private String telefone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_LOGIN")
    private Login login;

    @Column(name = "TG_INATIVO")
    private int inativo;

    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;

    @Column(name = "DH_ALTERACAO", nullable = true, insertable = true, updatable = true)
    private LocalDateTime dhAlteracao;

    public Cliente() {
    }

    public Cliente(Integer id, String cpf, String nomeCompleto, String dtNascimento, String sexo, String telefone, Login login, int inativo, LocalDateTime dhInclusao, LocalDateTime dhAlteracao) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.dtNascimento = dtNascimento;
        this.sexo = sexo;
        this.telefone = telefone;
        this.login = login;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
        this.dhAlteracao = dhAlteracao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public int getInativo() {
        return inativo;
    }

    public void setInativo(int inativo) {
        this.inativo = inativo;
    }

    public LocalDateTime getDhInclusao() {
        return dhInclusao;
    }

    public void setDhInclusao(LocalDateTime dhInclusao) {
        this.dhInclusao = dhInclusao;
    }

    public LocalDateTime getDhAlteracao() {
        return dhAlteracao;
    }

    public void setDhAlteracao(LocalDateTime dhAlteracao) {
        this.dhAlteracao = dhAlteracao;
    }

}
