package py.com.test.nurseweb.model.res;

/**
 * @author mlopez
 * @fecha 11/29/18,4:41 PM
 */


public class SignosVitalesDetails extends SignosVitales {

    private String estadoPresionArterial;
    private String estadoFrecuenciaCardiaca;

    public String getEstadoPresionArterial() {
        return estadoPresionArterial;
    }

    public void setEstadoPresionArterial(String estadoPresionArterial) {
        this.estadoPresionArterial = estadoPresionArterial;
    }

    public String getEstadoFrecuenciaCardiaca() {
        return estadoFrecuenciaCardiaca;
    }

    public void setEstadoFrecuenciaCardiaca(String estadoFrecuenciaCardiaca) {
        this.estadoFrecuenciaCardiaca = estadoFrecuenciaCardiaca;
    }

    public SignosVitalesDetails(String estadoPresionArterial, String estadoFrecuenciaCardiaca) {
        this.estadoPresionArterial = estadoPresionArterial;
        this.estadoFrecuenciaCardiaca = estadoFrecuenciaCardiaca;
    }

    public SignosVitalesDetails(SignosVitales signosVitales ,String estadoPresionArterial, String estadoFrecuenciaCardiaca) {
        super(signosVitales.getId(), signosVitales.getPaciente(), signosVitales.getSistolica(), signosVitales.getDiastolica(), signosVitales.getFrecuenciaCardiaca(), signosVitales.getFechaRegistro());
        this.estadoPresionArterial = estadoPresionArterial;
        this.estadoFrecuenciaCardiaca = estadoFrecuenciaCardiaca;
    }

    public SignosVitalesDetails() {
    }

    public SignosVitalesDetails(Integer id, Paciente paciente, Integer sistolica, Integer diastolica, Integer frecuenciaCardiaca, String fechaRegistro, String estadoPresionArterial, String estadoFrecuenciaCardiaca) {
        super(id, paciente, sistolica, diastolica, frecuenciaCardiaca, fechaRegistro);
        this.estadoPresionArterial = estadoPresionArterial;
        this.estadoFrecuenciaCardiaca = estadoFrecuenciaCardiaca;
    }

}
