package py.com.test.nurseweb.service.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import py.com.test.nurseweb.model.Estado;
import py.com.test.nurseweb.model.req.PacienteReq;
import py.com.test.nurseweb.model.req.SignosVitalesReq;
import py.com.test.nurseweb.model.res.*;
import py.com.test.nurseweb.service.NurseService;
import py.com.test.nurseweb.utils.PropertiesUtils;
import py.com.test.nurseweb.utils.RestCallerUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author mlopez
 * @fecha 11/30/18,2:25 PM
 */

@Service
public class NurseServiceImpl implements NurseService {

    private String url;

    public NurseServiceImpl(PropertiesUtils propertiesUtils){
        url = propertiesUtils.getApiEndpoint();
    }

    @Override
    public ListaPaciente obtenerPacientes(Estado estado) {
        String endpoint = url + "/pacientes";
        Map<String, Object> params = new HashMap<>();
        if(estado!=null)
            params.put("estado", estado.name());

        return RestCallerUtil.doGet(endpoint,ListaPaciente.class,params,new HttpHeaders());
    }

    @Override
    public Paciente obtenerPaciente(Integer id, Estado estado) {
        return null;
    }

    @Override
    public Paciente obtenerPacientePorCi(String ci, Estado estado) {
        String endpoint = url + "/paciente";
        Map<String, Object> params = new HashMap<>();
        params.put("ci", ci);
        return RestCallerUtil.doGet(endpoint,Paciente.class,params,new HttpHeaders());
    }

    @Override
    public Paciente insertarPaciente(PacienteReq req) {
        String endpoint = url + "/paciente";
        return RestCallerUtil.doPost(endpoint,req,Paciente.class,new HttpHeaders());
    }

    @Override
    public Paciente actualizarPaciente(Integer id, PacienteReq req) {
        String endpoint = url + "/paciente/"+id;
        return RestCallerUtil.doPut(endpoint,req,Paciente.class,new HttpHeaders());
    }

    @Override
    public ListaSignosVitales obtenerSignosVitales(Integer idPaciente) {
        String endpoint = url+ "/paciente/"+idPaciente+"/signos-vitales";
        return RestCallerUtil.doGet(endpoint,ListaSignosVitales.class,null,new HttpHeaders());
    }

    @Override
    public List<GraficoData> obtenerGraficoData(Integer idPaciente) {
        ListaSignosVitales signosVitales = obtenerSignosVitales(idPaciente);

        if(signosVitales==null || signosVitales.getSignosVitales().isEmpty())
            return null;

        List<GraficoData> datos = new ArrayList<>();

        for (SignosVitales signo: signosVitales.getSignosVitales()) {
            GraficoData graficoData = new GraficoData();
            graficoData.setIndexLabel(signo.getFechaRegistro());
            graficoData.setY(signo.getFrecuenciaCardiaca());
            graficoData.setMarkerType("triangle");
            datos.add(graficoData);
        }
        return datos;
    }

    @Override
    public SignosVitales insertarSignoVital(Integer idPaciente, SignosVitalesReq req) {
        String endpoint = url+ "/paciente/"+idPaciente+"/signos-vitales";
        return RestCallerUtil.doPost(endpoint,req,SignosVitales.class,new HttpHeaders());
    }

    @Override
    public SignosVitales actualizarSignoVital(Integer idSignovital, SignosVitalesReq req) {
        String endpoint = url + "/signos-vitales/"+idSignovital;
        return RestCallerUtil.doPut(endpoint,req, SignosVitales.class, new HttpHeaders());
    }

    @Override
    public void eliminarSignoVital(Integer idSignoVital) {
        String endpoint = url + "/signos-vitales/"+idSignoVital;
        RestCallerUtil.doDelete(endpoint, void.class, new HttpHeaders());
    }
}
