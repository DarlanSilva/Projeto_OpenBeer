package br.com.wda.OpenBeerProject.Entity;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 *
 * @author Darlan Silva
 */
@Component
public class FilterRel {

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime dtInicio;
    
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime dtFinal;

    public FilterRel() {
    }

    public FilterRel(LocalDateTime dtInicio, LocalDateTime dtFinal) {
        this.dtInicio = dtInicio;
        this.dtFinal = dtFinal;
    }

    public LocalDateTime getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDateTime dtInicio) {
        this.dtInicio = dtInicio;
    }

    public LocalDateTime getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(LocalDateTime dtFinal) {
        this.dtFinal = dtFinal;
    }

}
