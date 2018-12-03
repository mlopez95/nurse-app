package py.com.test.nurseapi.model.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author mlopez
 * @fecha 11/29/18,10:57 AM
 */

@ApiModel("Signos Vitales Request")
public class SignosVitalesReq {

    @ApiModelProperty("Presion Arterial Sistolica")
    @NotNull
    private Integer sistolica;

    @ApiModelProperty("Presion Arterial Diastolica")
    @NotNull
    private Integer diastolica;

    @ApiModelProperty("Frecuencia Cardiaca")
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
