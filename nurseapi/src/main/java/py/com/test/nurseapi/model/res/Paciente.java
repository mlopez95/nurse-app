package py.com.test.nurseapi.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import py.com.test.nurseapi.model.Estado;

import javax.validation.constraints.NotNull;

/**
 * @author mlopez
 * @fecha 11/29/18,9:51 AM
 */

@ApiModel(value = "Paciente", description = "Datos de un Paciente")
public class Paciente {

    @ApiModelProperty(value = "Identificador del Paciente")
    private Integer id;

    @ApiModelProperty(value = "Nombre del Paciente")
    private String nombre;

    @ApiModelProperty(value = "Cedula de Identidad del Paciente")
    private String ci;

    @ApiModelProperty(value = "Edad del Paciente")
    private Integer edad;

    @ApiModelProperty(value = "Fecha que se registro el paciente")
    private String fechaRegistro;

    @ApiModelProperty(value = "Estado del paciente", example = "ACTIVO")
    private Estado estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
