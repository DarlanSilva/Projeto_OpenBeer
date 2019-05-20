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
@Table(name = "VD_PEDIDO")
public class Pedido implements Serializable {

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "FK_CLIENTE")
    private Integer cliente;

    @Column(name = "FK_TIPOENTREGA")
    private Integer tipoEntrega;

    @Column(name = "FK_STATUSPEDIDO")
    private Integer status;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "VL_PEDIDO")
    private BigDecimal valorPedido;

    @Column(name = "DH_PEDIDOFECHADO", nullable = false, insertable = true, updatable = true)
    private LocalDateTime dhPedidoFechado;

    @Column(name = "DH_PEDIDOCANCELADO", nullable = false, insertable = true, updatable = true)
    private LocalDateTime dhPedidoCancelado;

    @Column(name = "DH_PREVICAOENTREGA", nullable = false, insertable = true, updatable = true)
    private LocalDateTime dhPrevisaoEntrega;

    @Column(name = "DH_ENTREGUE", nullable = false, insertable = true, updatable = true)
    private LocalDateTime dhEntregue;

    @Column(name = "TG_INATIVO")
    private int inativo;

    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;

    public Pedido() {
    }

    public Pedido(Integer id, Integer cliente, Integer tipoEntrega, Integer status, BigDecimal valorPedido, LocalDateTime dhPedidoFechado, LocalDateTime dhPedidoCancelado, LocalDateTime dhPrevisaoEntrega, LocalDateTime dhEntregue, int inativo, LocalDateTime dhInclusao) {
        this.id = id;
        this.cliente = cliente;
        this.tipoEntrega = tipoEntrega;
        this.status = status;
        this.valorPedido = valorPedido;
        this.dhPedidoFechado = dhPedidoFechado;
        this.dhPedidoCancelado = dhPedidoCancelado;
        this.dhPrevisaoEntrega = dhPrevisaoEntrega;
        this.dhEntregue = dhEntregue;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
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

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(Integer tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(BigDecimal valorPedido) {
        this.valorPedido = valorPedido;
    }

    public LocalDateTime getDhPedidoFechado() {
        return dhPedidoFechado;
    }

    public void setDhPedidoFechado(LocalDateTime dhPedidoFechado) {
        this.dhPedidoFechado = dhPedidoFechado;
    }

    public LocalDateTime getDhPedidoCancelado() {
        return dhPedidoCancelado;
    }

    public void setDhPedidoCancelado(LocalDateTime dhPedidoCancelado) {
        this.dhPedidoCancelado = dhPedidoCancelado;
    }

    public LocalDateTime getDhPrevisaoEntrega() {
        return dhPrevisaoEntrega;
    }

    public void setDhPrevisaoEntrega(LocalDateTime dhPrevisaoEntrega) {
        this.dhPrevisaoEntrega = dhPrevisaoEntrega;
    }

    public LocalDateTime getDhEntregue() {
        return dhEntregue;
    }

    public void setDhEntregue(LocalDateTime dhEntregue) {
        this.dhEntregue = dhEntregue;
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
    
    

}