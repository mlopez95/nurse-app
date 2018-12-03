package py.com.test.nurseweb.model.res;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mlopez
 * @fecha 11/29/18,4:32 PM
 */


public class SignosVitales {

    private Integer id;
    private Paciente paciente;
    private Integer sistolica;
    private Integer diastolica;
    private Integer frecuenciaCardiaca;
    private String fechaRegistro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public SignosVitales(Integer id, Paciente paciente, Integer sistolica, Integer diastolica, Integer frecuenciaCardiaca, String fechaRegistro) {
        this.id = id;
        this.paciente = paciente;
        this.sistolica = sistolica;
        this.diastolica = diastolica;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.fechaRegistro = fechaRegistro;
    }

    public SignosVitales() {
    }
}
