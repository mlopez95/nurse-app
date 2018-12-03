package py.com.test.nurseapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import py.com.test.nurseapi.error.APIException;
import py.com.test.nurseapi.model.Estado;
import py.com.test.nurseapi.model.req.PacienteReq;
import py.com.test.nurseapi.model.req.SignosVitalesReq;
import py.com.test.nurseapi.model.res.*;
import py.com.test.nurseapi.service.NurseService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mlopez
 * @fecha 11/29/18,9:39 AM
 */
@Api(value = "Nurse API", tags = "Nurse API")
@RestController
public class NurseAPI {

    public static final String PACIENTE_MAPPING = "/paciente";
    public static final String PACIENTES_MAPPING = "/pacientes";
    public static final String FRECUENCIA_CARDIACA_MAPPING = "/frecuencia";
    public static final String PRESION_ARTERIAL = "/presion";
    public static final String SIGNOS_VITALES = "/signos-vitales";


    @Autowired
    private NurseService service;


    //PACIENTES SERVICES
    @ApiOperation(value = PACIENTES_MAPPING, notes = "Servicio que retorna una lista de pacientes", nickname = "getPacientes")
    @GetMapping(PACIENTES_MAPPING)
    public ListaPaciente getPacientes(@RequestParam(value = "estado", required = false) Estado estado)  throws APIException {
        List<Paciente> pacientes =  service.obtenerPacientes(estado);
        return new ListaPaciente(pacientes);
    }

    @ApiOperation(value = PACIENTE_MAPPING+"/{id}", notes = "Servicio que retorna un paciente", nickname = "getPacientePorId")
    @GetMapping(PACIENTE_MAPPING+"/{id}")
    public Paciente getPacientePorId(@PathVariable("id") Integer id)  throws APIException{
        return service.obtenerPacientePorId(id);
    }

    @ApiOperation(value = PACIENTE_MAPPING, notes = "Servicio que retorna un paciente", nickname = "getPacientePorCiYEstado")
    @GetMapping(PACIENTE_MAPPING)
    public Paciente getPacientePorCiYEstado(
            @RequestParam("ci") String ci,
            @RequestParam(value = "estado", required = false) Estado estado) throws APIException {
        return service.obtenerPacientePorCi(ci,estado);
    }

    @ApiOperation(value = PACIENTE_MAPPING, notes = "Servicio que actualiza un paciente", nickname = "updatePaciente")
    @PutMapping(PACIENTE_MAPPING+"/{id}")
    public Paciente updatePaciente(
            @PathVariable("id") Integer id,
            @RequestBody @Valid PacienteReq req)  throws APIException{
        return service.actualizarPaciente(id,req);
    }

    @ApiOperation(value = PACIENTE_MAPPING, notes = "Servicio que crear un paciente", nickname = "createPaciente")
    @PostMapping(PACIENTE_MAPPING)
    public Paciente createPaciente(
            @RequestBody @Valid PacienteReq req) throws APIException {
        return service.createPaciente(req);
    }

    //SIGNOS VITALES SERVICES
    @ApiOperation(
            value = PACIENTE_MAPPING+"/{id_paciente}"+SIGNOS_VITALES,
            notes = "Servicio que guardar un signo vital a un paciente",
            nickname = "insertSignoVital"
    )
    @PostMapping(PACIENTE_MAPPING+"/{id_paciente}"+SIGNOS_VITALES)
    public SignosVitales insertSignoVital(
            @PathVariable("id_paciente") Integer idPaciente,
            @RequestBody @Valid SignosVitalesReq req) throws APIException {
        return service.insertSignosVitales(idPaciente, req);
    }

    @ApiOperation(value = PACIENTE_MAPPING, notes = "Servicio que actualiza un signo vital", nickname = "updateSignoVital")
    @PutMapping(SIGNOS_VITALES+"/{id}")
    public SignosVitales updateSignoVital(
            @PathVariable("id") Integer id,
            @RequestBody @Valid SignosVitalesReq req)  throws APIException{
        return service.updateSignosVitales(id,req);
    }

    @ApiOperation(value = PACIENTE_MAPPING, notes = "Servicio que elimina un signo vital", nickname = "deleteSignoVital")
    @DeleteMapping(SIGNOS_VITALES+"/{id}")
    public void deleteSignoVital(
            @PathVariable("id") Integer id)  throws APIException{
        service.deleteSignosVitales(id);
    }

    @ApiOperation(value = PACIENTE_MAPPING+"/{id_paciente}"+SIGNOS_VITALES, notes = "Servicio que retorna una lista de signos vitales", nickname = "obtenerSignosVitales")
    @GetMapping(PACIENTE_MAPPING+"/{id_paciente}"+SIGNOS_VITALES)
    public ListaSignosVitales obtenerSignosVitales(
            @PathVariable(value = "id_paciente") Integer idPaciente)  throws APIException {
        List<SignosVitalesDetails> pacientes =  service.obtenerSignosVitalesPorPaciente(idPaciente);
        ListaSignosVitales respuesta = new ListaSignosVitales();
        respuesta.setSignosVitales(pacientes);
        return respuesta;
    }



}
