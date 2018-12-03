package py.com.test.nurseapi.model.res;

import java.util.List;

/**
 * @author mlopez
 * @fecha 11/29/18,12:00 PM
 */


public class ListaPaciente {
    private List<Paciente> pacientes;

    public ListaPaciente() {
    }

    public ListaPaciente(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
}
