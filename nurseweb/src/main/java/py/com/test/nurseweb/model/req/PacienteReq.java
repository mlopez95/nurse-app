package py.com.test.nurseweb.model.req;


import py.com.test.nurseweb.model.Estado;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mlopez
 * @fecha 11/29/18,9:51 AM
 */

public class PacienteReq {

    @NotBlank
    private String nombre;

    @NotBlank
    private String ci;

    @NotNull
    private Integer edad;

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
