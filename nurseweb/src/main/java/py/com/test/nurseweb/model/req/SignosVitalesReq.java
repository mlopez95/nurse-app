package py.com.test.nurseweb.model.req;



import javax.validation.constraints.NotNull;

/**
 * @author mlopez
 * @fecha 11/29/18,10:57 AM
 */

public class SignosVitalesReq {

    @NotNull
    private Integer sistolica;

    @NotNull
    private Integer diastolica;

    @NotNull
    private Integer frecuenciaCardiaca;

    public Integer getSistolica() {
        return sistolica;
    }

    public void setSistolica(Integer sistolica) {
        this.sistolica = sistolica;
    }

    public Integer getDiastolica() {
        return diastolica;
    }

    public void setDiastolica(Integer diastolica) {
        this.diastolica = diastolica;
    }

    public Integer getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(Integer frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

}
