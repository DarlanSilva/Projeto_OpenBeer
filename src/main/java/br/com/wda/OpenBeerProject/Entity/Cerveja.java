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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
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
    private Long id;

    @Size(min = 1, max = 350, message = "NOME DE CERVEJA INVÁLIDO.")
    @Column(name = "CERVEJA")
    private String cerveja;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "DESCRICAO")
    private String descricao;

    @Digits(integer = 13, fraction = 2)
    @Size(min = 1, message = "CAMPO OBRIGATÓRIO")
    @Column(name = "VL_TORAL")
    private BigDecimal valorCerveja;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "CODIGO")
    private String codigoCerveja;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "MARCA")
    private String marca;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "FORNECEDOR")
    private String fornecedor;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "QUANTIDADE")
    private int quantidade;

    @Digits(integer = 13, fraction = 2)
    @Size(min = 1, message = "CAMPO OBRIGATÓRIO")
    @Column(name = "ML")
    private BigDecimal mlCerveja;

    @Column(name = "TG_INATIVO")
    private int inativo;

    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;

    @Column(name = "DH_ALTERACAO", nullable = false, insertable = true, updatable = true)
    private LocalDateTime dhAlteracao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TIPO_CERVEJA",
            joinColumns = @JoinColumn(name = "ID_CERVEJA"),
            inverseJoinColumns = @JoinColumn(name = "ID_TIPOCERVEJA")
    )

    private Set<TipoCerveja> tipoCerveja;

    @Transient
    private Set<Integer> idTiposCervejas;

    public Cerveja() {
    }

    public Cerveja(Long id, String cerveja, String descricao, BigDecimal valorCerveja, String codigoCerveja, String marca, String fornecedor, int quantidade, BigDecimal mlCerveja, int inativo, LocalDateTime dhInclusao, LocalDateTime dhAlteracao) {
        this.id = id;
        this.cerveja = cerveja;
        this.descricao = descricao;
        this.valorCerveja = valorCerveja;
        this.codigoCerveja = codigoCerveja;
        this.marca = marca;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
        this.mlCerveja = mlCerveja;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
        this.dhAlteracao = dhAlteracao;
    }

    public Cerveja(Long id, String cerveja, String descricao, BigDecimal valorCerveja, String codigoCerveja, String marca, String fornecedor, int quantidade, BigDecimal mlCerveja, int inativo, LocalDateTime dhInclusao, LocalDateTime dhAlteracao, Set<TipoCerveja> tipoCerveja, Set<Integer> idTiposCervejas) {
        this.id = id;
        this.cerveja = cerveja;
        this.descricao = descricao;
        this.valorCerveja = valorCerveja;
        this.codigoCerveja = codigoCerveja;
        this.marca = marca;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
        this.mlCerveja = mlCerveja;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
        this.dhAlteracao = dhAlteracao;
        this.tipoCerveja = tipoCerveja;
        this.idTiposCervejas = idTiposCervejas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCerveja() {
        return cerveja;
    }

    public void setCerveja(String cerveja) {
        this.cerveja = cerveja;
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

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getMlCerveja() {
        return mlCerveja;
    }

    public void setMlCerveja(BigDecimal mlCerveja) {
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

    public Set<TipoCerveja> getTipoCerveja() {
        return tipoCerveja;
    }

    public void setTipoCerveja(Set<TipoCerveja> tipoCerveja) {
        this.tipoCerveja = tipoCerveja;
    }

    public Set<Integer> getIdTiposCervejas() {
        return idTiposCervejas;
    }

    public void setIdTiposCervejas(Set<Integer> idTiposCervejas) {
        this.idTiposCervejas = idTiposCervejas;
    }

    
}
