package py.com.test.nurseweb.service;

import py.com.test.nurseweb.model.Estado;
import py.com.test.nurseweb.model.req.PacienteReq;
import py.com.test.nurseweb.model.req.SignosVitalesReq;
import py.com.test.nurseweb.model.res.*;

import java.util.List;

/**
 * @author mlopez
 * @fecha 11/30/18,2:26 PM
 */
public interface NurseService {
    ListaPaciente obtenerPacientes(Estado estado);
    Paciente obtenerPaciente(Integer id, Estado estado);
    Paciente obtenerPacientePorCi(String ci, Estado estado);
    Paciente insertarPaciente(PacienteReq req);
    Paciente actualizarPaciente(Integer id, PacienteReq req);

    ListaSignosVitales obtenerSignosVitales(Integer idPaciente);
    List<GraficoData> obtenerGraficoData(Integer idPaciente);
    SignosVitales insertarSignoVital(Integer idPaciente, SignosVitalesReq req);
    SignosVitales actualizarSignoVital(Integer idSignovital, SignosVitalesReq req);
    void eliminarSignoVital(Integer idSignoVital);





}
