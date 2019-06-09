package br.com.wda.OpenBeerProject.Entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Darlan Silva
 */
@Entity
@Table(name = "TB_TIPOENTREGA")
public class TipoEntrega {

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, max = 80)
    @Column(name = "TIPOENTREGA")
    private String tipoEntrega;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "VL_ENTREGA")
    private BigDecimal valorEntrega;
    
    @Column(name = "PRAZOENTREGA")
    private int prazoEntrega;

    @Column(name = "TG_INATIVO")
    private int inativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DH_INCLUSAO")
    private Date dhInclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DH_ALTERACAO")
    private Date dhAlteracao;
    
    @Transient
    private BigDecimal valorTotal;

    public TipoEntrega() {
    }

    public TipoEntrega(Integer id, String tipoEntrega, BigDecimal valorEntrega, int prazoEntrega, int inativo, Date dhInclusao, Date dhAlteracao) {
        this.id = id;
        this.tipoEntrega = tipoEntrega;
        this.valorEntrega = valorEntrega;
        this.prazoEntrega = prazoEntrega;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
        this.dhAlteracao = dhAlteracao;
    }

    public TipoEntrega(Integer id, String tipoEntrega, BigDecimal valorEntrega, int prazoEntrega, int inativo, Date dhInclusao, Date dhAlteracao, BigDecimal valorTotal) {
        this.id = id;
        this.tipoEntrega = tipoEntrega;
        this.valorEntrega = valorEntrega;
        this.prazoEntrega = prazoEntrega;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
        this.dhAlteracao = dhAlteracao;
        this.valorTotal = valorTotal;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public BigDecimal getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(BigDecimal valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public int getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(int prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public int getInativo() {
        return inativo;
    }

    public void setInativo(int inativo) {
        this.inativo = inativo;
    }

    public Date getDhInclusao() {
        return dhInclusao;
    }

    public void setDhInclusao(Date dhInclusao) {
        this.dhInclusao = dhInclusao;
    }

    public Date getDhAlteracao() {
        return dhAlteracao;
    }

    public void setDhAlteracao(Date dhAlteracao) {
        this.dhAlteracao = dhAlteracao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    
}
