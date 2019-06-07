package br.com.wda.OpenBeerProject.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "FK_CLIENTE")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "FK_TIPOENTREGA")
    private TipoEntrega TipoEntrega;
    
    @ManyToOne
    @JoinColumn(name = "FK_STATUSPEDIDO")
    private StatusPedido status;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "VL_PEDIDO")
    private BigDecimal valorPedido;

    @Column(name = "DH_PEDIDOFECHADO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhPedidoFechado;

    @Column(name = "DH_PEDIDOCANCELADO", nullable = true, insertable = true, updatable = false)
    private LocalDateTime dhPedidoCancelado;

    @Column(name = "DH_PREVICAOENTREGA", nullable = true, insertable = true, updatable = true)
    private LocalDateTime dhPrevisaoEntrega;

    @Column(name = "DH_ENTREGUE", nullable = true, insertable = true, updatable = false)
    private LocalDateTime dhEntregue;

    @Column(name = "TG_INATIVO")
    private int inativo;

    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;

    public Pedido() {
    }

    public Pedido(Integer id, Cliente cliente, TipoEntrega TipoEntrega, StatusPedido status, BigDecimal valorPedido, LocalDateTime dhPedidoFechado, LocalDateTime dhPedidoCancelado, LocalDateTime dhPrevisaoEntrega, LocalDateTime dhEntregue, int inativo, LocalDateTime dhInclusao) {
        this.id = id;
        this.cliente = cliente;
        this.TipoEntrega = TipoEntrega;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoEntrega getTipoEntrega() {
        return TipoEntrega;
    }

    public void setTipoEntrega(TipoEntrega TipoEntrega) {
        this.TipoEntrega = TipoEntrega;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
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
