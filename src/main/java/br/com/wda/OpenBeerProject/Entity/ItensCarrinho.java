package br.com.wda.OpenBeerProject.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

/**
 *
 * @author Darlan Silva
 */

@Entity
@Table(name = "VD_ITENSCARRINHO")
public class ItensCarrinho implements Serializable{
    
    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "FK_CLIENTE")
    private Integer cliente;
    
    @Column(name = "FK_CERVEJA")
    private Integer cerveja;
    
    @Column(name = "QUANTIDADE")
    private int quantidade;
    
    @Digits(integer = 13, fraction = 2)
    @Column(name = "VL_TOTAL")
    private BigDecimal vlTotal;

    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;
    
    @Column(name = "DH_COMPRAR", nullable = true, insertable = true, updatable = false)
    private LocalDateTime dhCompra;

    public ItensCarrinho() {
    }

    public ItensCarrinho(Integer id, Integer cliente, Integer cerveja, int quantidade, BigDecimal vlTotal, LocalDateTime dhInclusao, LocalDateTime dhCompra) {
        this.id = id;
        this.cliente = cliente;
        this.cerveja = cerveja;
        this.quantidade = quantidade;
        this.vlTotal = vlTotal;
        this.dhInclusao = dhInclusao;
        this.dhCompra = dhCompra;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer carrinho) {
        this.cliente = carrinho;
    }

    public Integer getCerveja() {
        return cerveja;
    }

    public void setCerveja(Integer cerveja) {
        this.cerveja = cerveja;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(BigDecimal vlTotal) {
        this.vlTotal = vlTotal;
    }

    public LocalDateTime getDhInclusao() {
        return dhInclusao;
    }

    public void setDhInclusao(LocalDateTime dhInclusao) {
        this.dhInclusao = dhInclusao;
    }

    public LocalDateTime getDhCompra() {
        return dhCompra;
    }

    public void setDhCompra(LocalDateTime dhCompra) {
        this.dhCompra = dhCompra;
    }
    
    

}