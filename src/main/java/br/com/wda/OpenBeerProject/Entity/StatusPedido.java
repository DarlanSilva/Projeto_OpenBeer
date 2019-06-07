package br.com.wda.OpenBeerProject.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Darlan Silva
 */

@Entity
@Table(name="TB_STATUSPEDIDO")
public class StatusPedido implements Serializable{
    
    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "STATUSPEDIDO")
    private String status;
    
    @Column(name = "TG_INATIVO")
    private int inativo;

    @Column(name = "DH_INCLUSAO", nullable = false, insertable = true, updatable = false)
    private LocalDateTime dhInclusao;

    public StatusPedido() {
    }

    public StatusPedido(Integer id, String status, int inativo, LocalDateTime dhInclusao) {
        this.id = id;
        this.status = status;
        this.inativo = inativo;
        this.dhInclusao = dhInclusao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
