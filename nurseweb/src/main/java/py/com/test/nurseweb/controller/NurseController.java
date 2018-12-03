package py.com.test.nurseweb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import py.com.test.nurseweb.model.Estado;
import py.com.test.nurseweb.model.req.PacienteReq;
import py.com.test.nurseweb.model.req.SignosVitalesReq;
import py.com.test.nurseweb.model.res.*;
import py.com.test.nurseweb.service.NurseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NurseController {

    public static final String INIT = "/";
    public static final String PACIENTES = "/pacientes";
    public static final String PACIENTE = "/paciente";

    public static final String SIGNOS_VITALES = "/signos-vitales";
    public static final String GRAFICO = "/signos-vitales-grafico";


    //VIEWS
    public static final String VIEW_HOME = "common/home";
    public static final String VIEW_ENFERMERA = "enfermera/lista";
    public static final String VIEW_SIGNOS_VITALES = "signovitales/lista";
    public static final String VIEW_PACIENTE_SIGNOS_VITALES = "pacientesignovitales/lista";

    //redirect
    public static final String REDIRECT = "redirect:";

    @Autowired
    private NurseService service;

    @RequestMapping(value=INIT, method = RequestMethod.GET)
    public String init(HttpSession session){
        return VIEW_HOME;
    }

    @RequestMapping(value = PACIENTES, method = RequestMethod.GET)
    public String enfermera(HttpServletRequest request, HttpServletResponse response, Model model){
        ListaPaciente listaPaciente = service.obtenerPacientes(Estado.ACTIVO);
        if(listaPaciente==null)
            model.addAttribute("pacienteList", new ArrayList<>());
        else
            model.addAttribute("pacienteList", listaPaciente.getPacientes());
        return VIEW_ENFERMERA;
    }

    @ResponseBody
    @RequestMapping(value = PACIENTE, method = RequestMethod.POST)
    public Paciente insertarPaciente(@RequestBody PacienteReq req, HttpServletRequest request, HttpServletResponse response, Model model){
        return service.insertarPaciente(req);
    }

    @ResponseBody
    @RequestMapping(value = PACIENTE +"/{id}", method = RequestMethod.PUT)
    public Paciente actualizarPaciente(@PathVariable("id") Integer id,
            @RequestBody PacienteReq req, HttpServletRequest request, HttpServletResponse response, Model model){
        return service.actualizarPaciente(id, req);
    }


    //TODO: SIGNOS VITALES

    @ResponseBody
    @RequestMapping(value = SIGNOS_VITALES +"/{id}", method = RequestMethod.DELETE)
    public void eliminarPaciente(@PathVariable("id") Integer id,
                                       HttpServletRequest request, HttpServletResponse response, Model model){
        service.eliminarSignoVital(id);
    }

    @ResponseBody
    @RequestMapping(value = SIGNOS_VITALES +"/{id}", method = RequestMethod.PUT)
    public SignosVitales actualizarSignoVital(@PathVariable("id") Integer id,
                                              @RequestBody SignosVitalesReq req, HttpServletRequest request, HttpServletResponse response, Model model){
        return service.actualizarSignoVital(id, req);
    }

    @ResponseBody
    @RequestMapping(value = SIGNOS_VITALES +"/{id_paciente}"+ PACIENTE, method = RequestMethod.POST)
    public SignosVitales crearSignoVital(@PathVariable("id_paciente") Integer idPaciente,
                                              @RequestBody SignosVitalesReq req, HttpServletRequest request, HttpServletResponse response, Model model){
        return service.insertarSignoVital(idPaciente, req);
    }

    @RequestMapping(value = SIGNOS_VITALES+"/{id_paciente}", method = RequestMethod.GET)
    public String obtenerSignosVitales(@PathVariable("id_paciente") Integer id,
            HttpServletRequest request, HttpServletResponse response, Model model){

        model.addAttribute("idPaciente",id);

        ListaSignosVitales respuesta = service.obtenerSignosVitales(id);
        if(respuesta==null)
            model.addAttribute("signosList", new ArrayList<>());
        else
            model.addAttribute("signosList", respuesta.getSignosVitales());
        return VIEW_SIGNOS_VITALES;
    }

    @ResponseBody
    @RequestMapping(value = GRAFICO+"/{id_paciente}", method = RequestMethod.GET)
    public List<GraficoData> obtenerDatosGrafico(@PathVariable("id_paciente") Integer id,
                                                 HttpServletRequest request, HttpServletResponse response, Model model){
        return service.obtenerGraficoData(id);
    }

    @RequestMapping(value = SIGNOS_VITALES+"/consultar", method = RequestMethod.GET)
    public String obtenerSignosVitales(@RequestParam("ci") String ci, HttpSession session,
                                       HttpServletRequest request, HttpServletResponse response, Model model){

        Paciente paciente = service.obtenerPacientePorCi(ci,Estado.ACTIVO);
        if(paciente==null){
            session.setAttribute("errorMsg", "El paciente no existe");
            return REDIRECT+INIT;
        }
        model.addAttribute("paciente", paciente);
        session.removeAttribute("errorMsg");

        ListaSignosVitales respuesta = service.obtenerSignosVitales(paciente.getId());
        model.addAttribute("idPaciente",paciente.getId());
        if(respuesta==null)
            model.addAttribute("signosList", new ArrayList<>());
        else
            model.addAttribute("signosList", respuesta.getSignosVitales());
        return VIEW_PACIENTE_SIGNOS_VITALES;
    }


}
