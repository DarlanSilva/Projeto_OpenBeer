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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Darlan Silva
 */
@Entity
@Table(name = "VD_PEDIDOITENS")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PedidoItens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_PEDIDO")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "FK_CERVEJA")
    private Cerveja cerveja;

    @Column(name = "QUANTIDADE")
    private int quantidade;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "VL_TOTAL")
    private BigDecimal vlTotal;

    @Digits(integer = 13, fraction = 2)
    @Column(name = "VL_UNITARIO")
    private BigDecimal vlUnitario;

    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;

    public PedidoItens() {
    }

    public PedidoItens(Integer id, Pedido pedido, Cerveja cerveja, int quantidade, BigDecimal vlTotal, BigDecimal vlUnitario, LocalDateTime dhInclusao) {
        this.id = id;
        this.pedido = pedido;
        this.cerveja = cerveja;
        this.quantidade = quantidade;
        this.vlTotal = vlTotal;
        this.vlUnitario = vlUnitario;
        this.dhInclusao = dhInclusao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Cerveja getCerveja() {
        return cerveja;
    }

    public void setCerveja(Cerveja cerveja) {
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

    public BigDecimal getVlUnitario() {
        return vlUnitario;
    }

    public void setVlUnitario(BigDecimal vlUnitario) {
        this.vlUnitario = vlUnitario;
    }    

    public LocalDateTime getDhInclusao() {
        return dhInclusao;
    }

    public void setDhInclusao(LocalDateTime dhInclusao) {
        this.dhInclusao = dhInclusao;
    }

}
