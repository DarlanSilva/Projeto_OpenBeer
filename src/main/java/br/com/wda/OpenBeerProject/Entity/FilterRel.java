package br.com.wda.OpenBeerProject.Entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 *
 * @author Darlan Silva
 */
@Component
public class FilterRel {

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    //@Temporal(TemporalType.DATE)
    private String dtInicio;
    
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    //@Temporal(TemporalType.DATE)
    private String dtFinal;

    public FilterRel() {
    }

    public FilterRel(String dtInicio, String dtFinal) {
        this.dtInicio = dtInicio;
        this.dtFinal = dtFinal;
    }

    public String getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(String dtInicio) {
        this.dtInicio = dtInicio;
    }

    public String getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(String dtFinal) {
        this.dtFinal = dtFinal;
    }

    
    
}
