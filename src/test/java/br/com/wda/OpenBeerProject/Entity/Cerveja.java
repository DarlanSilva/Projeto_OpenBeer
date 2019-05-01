package br.com.wda.OpenBeerProject.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 *
 */
@Entity
@Table(name = "TB_CERVEJA")
public class Cerveja implements Serializable {

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Size(min = 1, max = 350, message = "NOME DE CERVEJA INVÁLIDO.")
    @Column(name = "CERVEJA")
    private String cerveja;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "CERVEJA")
    private String descricao;

    @Digits(integer = 13, fraction = 2)
    @Size(min = 1, message = "CAMPO OBRIGATÓRIO")
    @Column(name = "VL_TORAL")
    private BigDecimal valorCerveja;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "CODIGO")
    private String codigoCerveja;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "MARCA")
    private String marca;

    @Size(min = 1, message = "CAMPO OBRIGATÓRIO.")
    @Column(name = "FORNECEDOR")
    private String fornecedor;

    @Digits(integer = 13, fraction = 2)
    @Size(min = 1, message = "CAMPO OBRIGATÓRIO")
    @Column(name = "ML")
    private BigDecimal mlCerveja;

    @Column(name = "TG_INATIVO")
    private int inativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DH_INCLUSAO")
    private Date dhInclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DH_ALTERACAO")
    private Date dhAlteracao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TIPO_CERVEJA",
            joinColumns = @JoinColumn(name = "ID_CERVEJA"),
            inverseJoinColumns = @JoinColumn(name = "ID_TIPOCERVEJA")
    )
    
    private Set<TipoCerveja> tipoCerveja;

}

