package br.com.wda.OpenBeerProject.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

/**
 *
 * @author Darlan Silva
 */
@Entity
@Table(name = "TB_CUPOM")
public class Cupom {

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(min=1, max=60)
    @Column(name="DS_CUPOM")
    private String cupom;
    
    @Digits(integer = 13, fraction = 2)
    @Column(name = "VL_CUPOM")
    private BigDecimal valorCupom;
    
    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;
    
    @Column(name = "DH_VALIDADE", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhValidade;
    
    @Transient
    private BigDecimal valorTotal;

    public Cupom() {
    }

    public Cupom(Integer id, String cupom, BigDecimal valorCupom, LocalDateTime dhInclusao, LocalDateTime dhValidade) {
        this.id = id;
        this.cupom = cupom;
        this.valorCupom = valorCupom;
        this.dhInclusao = dhInclusao;
        this.dhValidade = dhValidade;
    }

    public Cupom(Integer id, String cupom, BigDecimal valorCupom, LocalDateTime dhInclusao, LocalDateTime dhValidade, BigDecimal valorTotal) {
        this.id = id;
        this.cupom = cupom;
        this.valorCupom = valorCupom;
        this.dhInclusao = dhInclusao;
        this.dhValidade = dhValidade;
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCupom() {
        return cupom;
    }

    public void setCupom(String cupom) {
        this.cupom = cupom;
    }

    public BigDecimal getValorCupom() {
        return valorCupom;
    }

    public void setValorCupom(BigDecimal valorCupom) {
        this.valorCupom = valorCupom;
    }

    public LocalDateTime getDhInclusao() {
        return dhInclusao;
    }

    public void setDhInclusao(LocalDateTime dhInclusao) {
        this.dhInclusao = dhInclusao;
    }

    public LocalDateTime getDhValidade() {
        return dhValidade;
    }

    public void setDhValidade(LocalDateTime dhValidade) {
        this.dhValidade = dhValidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }    
    
}
