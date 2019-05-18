package br.com.wda.OpenBeerProject.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
@Entity
@Table(name = "TB_CERVEJA")
public class Cerveja implements Serializable {

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, max = 350)
    @NotBlank(message = "CAMPO NOME DA CERVEJA OBRIGATÓRIO")
    @Column(name = "CERVEJA")
    private String nome;

    @NotBlank(message = "CAMPO DESCRIÇÃO OBRIGATÓRIO")
    @Column(name = "DESCRICAO")
    private String descricao;

    @Digits(integer = 13, fraction = 2)
    @NotNull(message = "CAMPO VALOR TOTAL OBRIGATÓRIO")
    @Column(name = "VL_TOTAL")
    private BigDecimal valorCerveja;

    @Size(min = 1, max = 17)
    @NotBlank(message = "CAMPO CÓDIGO OBRIGATÓRIO")
    @Column(name = "CODIGO")
    private String codigoCerveja;

    @Size(min = 1, max = 100)
    @NotBlank(message = "CAMPO MARCA OBRIGATÓRIO")
    @Column(name = "MARCA")
    private String marca;

    @NotNull(message = "CAMPO TEOR OBRIGATÓRIO")
    @Column(name = "TEOR")
    private int teor;

    @NotNull(message = "CAMPO QUANTIDADE OBRIGATÓRIO")
    @Column(name = "QUANTIDADE")
    private int quantidade;

    @Size(min = 1, max = 15)
    @NotBlank(message = "CAMPO ML/L OBRIGATÓRIO")
    @Column(name = "ML")
    private String mlCerveja;

    @Column(name = "TG_INATIVO")
    private int inativo;

    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;

    @Column(name = "DH_ALTERACAO", nullable = true, insertable = true, updatable = true)
    private LocalDateTime dhAlteracao;
    
    @JoinTable(name="TB_TIPOCERVEJA")
    @Column(name = "FK_TIPOCERVEJA")
    private Integer tipoCerveja;

    public Cerveja() { 
    }

    public Cerveja(Integer id, String nome, String descricao, BigDecimal valorCerveja, String codigoCerveja, String marca, int teor, int quantidade, String mlCerveja, int inativo, LocalDateTime dhInclusao, LocalDateTime dhAlteracao, Integer tipoCerveja) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorCerveja = valorCerveja;
        this.codigoCerveja = codigoCerveja;
        this.marca = marca;
        this.teor = teor;
        this.quantidade = quantidade;
        this.mlCerveja = mlCerveja;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
        this.dhAlteracao = dhAlteracao;
        this.tipoCerveja = tipoCerveja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorCerveja() {
        return valorCerveja;
    }

    public void setValorCerveja(BigDecimal valorCerveja) {
        this.valorCerveja = valorCerveja;
    }

    public String getCodigoCerveja() {
        return codigoCerveja;
    }

    public void setCodigoCerveja(String codigoCerveja) {
        this.codigoCerveja = codigoCerveja;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getTeor() {
        return teor;
    }

    public void setTeor(int teor) {
        this.teor = teor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getMlCerveja() {
        return mlCerveja;
    }

    public void setMlCerveja(String mlCerveja) {
        this.mlCerveja = mlCerveja;
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

    public Integer getTipoCerveja() {
        return tipoCerveja;
    }

    public void setTipoCerveja(Integer tipoCerveja) {
        this.tipoCerveja = tipoCerveja;
    }

}
