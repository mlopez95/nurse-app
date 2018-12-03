package py.com.test.nurseapi.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import py.com.test.nurseapi.model.Estado;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mlopez
 * @fecha 11/29/18,9:51 AM
 */

@ApiModel(value = "PacienteReq", description = "Datos de un Paciente")
public class PacienteReq {

    @ApiModelProperty(value = "Nombre del Paciente")
    @NotBlank
    private String nombre;

    @ApiModelProperty(value = "Cedula de Identidad del Paciente")
    @NotBlank
    private String ci;

    @ApiModelProperty(value = "Edad del Paciente")
    @NotNull
    private Integer edad;

    @ApiModelProperty(value = "Estado del paciente", example = "ACTIVO")
    @NotNull
    private Estado estado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
