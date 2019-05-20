package br.com.wda.OpenBeerProject.Entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Darlan Silva
 */

public class CarrinhoItem {

    private Cerveja cerveja;

    public CarrinhoItem(Cerveja cerveja) {
        this.cerveja = cerveja;
    }

    public Cerveja getCerveja() {
        return cerveja;
    }

    public void setCerveja(Cerveja cerveja) {
        this.cerveja = cerveja;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.cerveja);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CarrinhoItem other = (CarrinhoItem) obj;
        if (!Objects.equals(this.cerveja, other.cerveja)) {
            return false;
        }
        return true;
    }

    public BigDecimal getTotal(int quantidade) {
        return cerveja.getValorCerveja().multiply(new BigDecimal(quantidade));
    }

}
