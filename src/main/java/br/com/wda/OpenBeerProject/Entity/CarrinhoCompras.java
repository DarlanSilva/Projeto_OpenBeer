package br.com.wda.OpenBeerProject.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Darlan Silva
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable {

    private static final long serialVersionUID = 1L;

    public Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();

    private BigDecimal valorEntrega;
    private BigDecimal valorDesconto;
    private int prazoEntrega;

    private Cliente cliente;
    private Endereco endereco;
    private TipoEntrega tipoEntrega;

    public Collection<CarrinhoItem> getItens() {
        return itens.keySet();
    }

    public void add(CarrinhoItem item) {
        itens.put(item, getQuantidade(item) + 1);
    }

    public int getQuantidade(CarrinhoItem item) {
        if (!itens.containsKey(item)) {
            itens.put(item, 0);
        }

        return itens.get(item);
    }

    public int getQuantidade() {
        return itens.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador));
    }

    public BigDecimal getTotal(CarrinhoItem item) {
        return item.getTotal(getQuantidade(item));
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (CarrinhoItem item : itens.keySet()) {
            total = total.add(getTotal(item));
        }
        return total;
    }

    public void remover(Integer cervejaId) {
        Cerveja cerveja = new Cerveja();
        cerveja.setId(cervejaId);

        itens.remove(new CarrinhoItem(cerveja));
    }

    public BigDecimal getTotalCompra() {
        BigDecimal valorEntrega = getValorEntrega();
        if (valorEntrega == null) {
            valorEntrega = BigDecimal.ZERO;
        }

        BigDecimal valorDesconto = getValorDesconto();
        if (valorDesconto == null) {
            valorDesconto = BigDecimal.ZERO;
        }

        BigDecimal totalBruto = getTotal();
        BigDecimal totalCompra = totalBruto.add(valorEntrega).subtract(valorDesconto);

        return totalCompra;

    }
    
    public Integer getIdTipoEntrega(){
        return tipoEntrega.getId();
    }

    public BigDecimal getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(BigDecimal valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public int getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(int prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }
    
    

}
