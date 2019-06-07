/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wda.OpenBeerProject.Entity;

import br.com.wda.OpenBeerProject.Configuration.SecurityConfig;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 */

@Entity
@Table(name = "TS_LOGIN")
public class Login implements UserDetails{
    
    //private List<Permissao> papeis;
    
    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "EMAIL INVÁLIDO")
    @Column(unique = true, name = "EMAIL")
    private String email;
    
    @NotBlank(message = "CAMPO SENHA OBRIGATÓRIO")
    @Column(name = "SENHA")
    private String hashSenha;
    
    @OneToOne
    @JoinColumn(name = "FK_PERMISSAOACESSO")
    private Permissao permissaoAcesso;
    
    @Column(name = "TG_INATIVO")
    private int inativo;
    
    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;

    @Column(name = "DH_ALTERACAO", nullable = true, insertable = true, updatable = true)
    private LocalDateTime dhAlteracao;

    public Login() {
    }

    public Login(Integer id, String email, String hashSenha, Permissao permissaoAcesso, int inativo, LocalDateTime dhInclusao, LocalDateTime dhAlteracao) {
        this.id = id;
        this.email = email;
        this.hashSenha = hashSenha;
        this.permissaoAcesso = permissaoAcesso;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
        this.dhAlteracao = dhAlteracao;
    }
    
//    public final void setSenha(String senhaAberta){
//        this.hashSenha = SecurityConfig.bcryptPasswordEncoder().encode(senhaAberta);
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashSenha() {
        return hashSenha;
    }

    public void setHashSenha(String senhaAberta) {
        this.hashSenha = SecurityConfig.bcryptPasswordEncoder().encode(senhaAberta);
    }

    public Permissao getPermissaoAcesso() {
        return permissaoAcesso;
    }

    public void setPermissaoAcesso(Permissao permissaoAcesso) {
        this.permissaoAcesso = permissaoAcesso;
    }        

//    public List<Permissao> getPermissaoAcesso() {
//        return permissaoAcesso;
//    }
//
//    public void setPermissaoAcesso(List<Permissao> permissaoAcesso) {
//        this.permissaoAcesso = permissaoAcesso;
//    }     

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
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return getHashSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
