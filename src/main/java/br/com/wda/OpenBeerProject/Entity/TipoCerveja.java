package br.com.wda.OpenBeerProject.Entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
@Entity
@Table(name = "TB_TIPOCERVEJA")
public class TipoCerveja {

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(min = 1, max = 80, message = "TIPO DA CERVEJA INVÁLIDO.")
    @Column(name = "TIPOCERVEJA")
    private String tipoCerveja;
    
    @Column(name = "TG_INATIVO")
    private int inativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DH_INCLUSAO")
    private Date dhInclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DH_ALTERACAO")
    private Date dhAlteracao;

    public TipoCerveja() {
    }

    public TipoCerveja(Integer id, String tipoCerveja) {
        this.id = id;
        this.tipoCerveja = tipoCerveja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoCerveja() {
        return tipoCerveja;
    }

    public void setTipoCerveja(String tipoCerveja) {
        this.tipoCerveja = tipoCerveja;
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
    
    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + ", tipoCerveja=" + tipoCerveja + '}';
    }

    
    

}
