package py.com.test.nurseapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.test.nurseapi.dao.NurseDao;
import py.com.test.nurseapi.error.APIException;
import py.com.test.nurseapi.model.Estado;
import py.com.test.nurseapi.model.req.PacienteReq;
import py.com.test.nurseapi.model.req.SignosVitalesReq;
import py.com.test.nurseapi.model.res.*;
import py.com.test.nurseapi.service.NurseService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mlopez
 * @fecha 11/29/18,12:02 PM
 */

@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseDao dao;

    @Override
    public Paciente createPaciente(PacienteReq req) throws APIException {
        return dao.createPaciente(req);
    }

    @Override
    public Paciente actualizarPaciente(Integer id, PacienteReq req) throws APIException {
        return dao.actualizarPaciente(id,req);
    }

    @Override
    public List<Paciente> obtenerPacientes(Estado estado) throws APIException {

        return dao.obtenerPacientes(estado);
    }

    @Override
    public Paciente obtenerPacientePorCi(String ci, Estado estado) throws APIException {
        return dao.obtenerPacientePorCi(ci, estado);
    }

    @Override
    public Paciente obtenerPacientePorId(Integer id) throws APIException {
        return dao.obtenerPacientePorId(id);
    }

    @Override
    public SignosVitales insertSignosVitales(Integer idPaciente, SignosVitalesReq req) throws APIException {
        Paciente paciente = dao.obtenerPacientePorId(idPaciente);
        return dao.insertSignosVitales(paciente.getId(), req);
    }

    @Override
    public SignosVitales obtenerSignosVitalesPorId(Integer id) throws APIException {
        return dao.obtenerSignosVitalesPorId(id);
    }

    @Override
    public SignosVitales updateSignosVitales(Integer id, SignosVitalesReq req) throws APIException {
        return dao.updateSignosVitales(id, req);
    }

    @Override
    public void deleteSignosVitales(Integer idSignoVital) throws APIException {
        dao.deleteSignosVitales(idSignoVital);
    }

    @Override
    public List<SignosVitalesDetails> obtenerSignosVitalesPorPaciente(Integer idPaciente) throws APIException {
        Paciente paciente = dao.obtenerPacientePorId(idPaciente);

        List<SignosVitales> signosVitales = dao.obtenerSignosVitalesPorPaciente(idPaciente);
        List<SignosVitalesDetails> signosVitalesDetails = new ArrayList<>();
        for (SignosVitales signos: signosVitales) {
            signos.setPaciente(paciente);
            String estadofrecuenciaCardiaca = estadoFrecueciaCardiaca(signos.getFrecuenciaCardiaca());
            String estadoPresionArterial = estadoPresionArterial(signos.getSistolica(), signos.getDiastolica());
            SignosVitalesDetails signosVitalesDetails1 = new SignosVitalesDetails(signos, estadoPresionArterial, estadofrecuenciaCardiaca);
            signosVitalesDetails.add(signosVitalesDetails1);
        }
//        notificaciones.stream().sorted(Comparator.comparing(Notificacion::getFechaTransaccion).reversed()).collect(Collectors.toList())
        return signosVitalesDetails.stream().sorted(Comparator.comparing(SignosVitalesDetails::getEstadoFrecuenciaCardiacaDate).reversed()).collect(Collectors.toList());
    }

    @Override
    public String estadoPresionArterial(Integer sistolica, Integer diastolica) throws APIException {
        List<RangoPresionArterial> rangos = dao.obtenerRangosPresionArterial();

        for (RangoPresionArterial rango:rangos) {
            String [] sistolicaArray = rango.getSistolica().split("-");
            String [] diastolicaArray = rango.getDiastolica().split("-");
            if(rango.getCategoria().equalsIgnoreCase("NORMAL")){
                if(
                        (sistolica >=Integer.parseInt(sistolicaArray[0]) && sistolica<Integer.parseInt(sistolicaArray[1]))
                                &&  (diastolica>=Integer.parseInt(diastolicaArray[0]) && diastolica<Integer.parseInt(diastolicaArray[1]))
                ) {
                    return rango.getCategoria();
                }
            }else{
                if(
                        (sistolica >=Integer.parseInt(sistolicaArray[0]) && sistolica<Integer.parseInt(sistolicaArray[1]))
                                ||  (diastolica>=Integer.parseInt(diastolicaArray[0]) && diastolica<Integer.parseInt(diastolicaArray[1]))
                ) {
                    return rango.getCategoria();
                }
            }


        }
        return "FUERA DE RANGO";
    }

    @Override
    public String estadoFrecueciaCardiaca(Integer frecuencia) throws APIException {
        FrecuenciaCardiacaNormal frecuenciaCardiacaNormal = dao.obtenerFrecuenciaCardiacaNormal();
        if(frecuenciaCardiacaNormal.getRangoMayor()<frecuencia)
            return "ALTA";

        if(frecuenciaCardiacaNormal.getRangoMenor()>frecuencia)
            return "BAJA";

        return "NORMAL";
    }
}
