package py.com.test.nurseapi.dao;

import py.com.test.nurseapi.error.APIException;
import py.com.test.nurseapi.model.Estado;
import py.com.test.nurseapi.model.req.PacienteReq;
import py.com.test.nurseapi.model.req.SignosVitalesReq;
import py.com.test.nurseapi.model.res.FrecuenciaCardiacaNormal;
import py.com.test.nurseapi.model.res.Paciente;
import py.com.test.nurseapi.model.res.RangoPresionArterial;
import py.com.test.nurseapi.model.res.SignosVitales;

import java.util.List;

/**
 * @author mlopez
 * @fecha 11/29/18,10:46 AM
 */
public interface NurseDao {

    //PACIENTE
    Paciente createPaciente(PacienteReq req) throws APIException;
    Paciente actualizarPaciente(Integer id ,PacienteReq req) throws APIException;
    List<Paciente> obtenerPacientes(Estado estado) throws APIException;
    Paciente obtenerPacientePorCi(String ci, Estado estado) throws APIException;
    Paciente obtenerPacientePorId(Integer id) throws APIException;

    //SIGNOS VITALES
    SignosVitales insertSignosVitales(Integer idPaciente, SignosVitalesReq req) throws APIException;
    List<SignosVitales> obtenerSignosVitalesPorPaciente(Integer idPaciente) throws APIException;
    SignosVitales obtenerSignosVitalesPorId(Integer id) throws APIException;
    SignosVitales updateSignosVitales(Integer id, SignosVitalesReq req) throws APIException;
    void deleteSignosVitales(Integer idSignoVital) throws APIException;

    List<RangoPresionArterial> obtenerRangosPresionArterial() throws APIException;
    FrecuenciaCardiacaNormal obtenerFrecuenciaCardiacaNormal() throws APIException;

}
