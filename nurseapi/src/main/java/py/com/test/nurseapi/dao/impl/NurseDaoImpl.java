package py.com.test.nurseapi.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import py.com.test.nurseapi.dao.NurseDao;
import py.com.test.nurseapi.error.APIException;
import py.com.test.nurseapi.error.APIExceptionType;
import py.com.test.nurseapi.error.ErrorCode;
import py.com.test.nurseapi.model.Estado;
import py.com.test.nurseapi.model.req.PacienteReq;
import py.com.test.nurseapi.model.req.SignosVitalesReq;
import py.com.test.nurseapi.model.res.FrecuenciaCardiacaNormal;
import py.com.test.nurseapi.model.res.Paciente;
import py.com.test.nurseapi.model.res.RangoPresionArterial;
import py.com.test.nurseapi.model.res.SignosVitales;
import py.com.test.nurseapi.util.NurseUtil;

import java.sql.*;
import java.util.List;
import java.util.Objects;

/**
 * @author mlopez
 * @fecha 11/29/18,11:11 AM
 */

@Repository
public class NurseDaoImpl implements NurseDao {

    private Logger logger = LoggerFactory.getLogger(NurseDaoImpl.class);

    public static final String TABLE_NAME_PACIENTE = "public.paciente";
    public static final String TABLE_NAME_SIGNOS_VITALES = "public.paciente";

    //PACIENTES SQL
    private static final String INSERT_PACIENTE = "INSERT INTO public.paciente(nombre, ci, edad, estado) VALUES (?, ?, ?, 'ACTIVO')";
    private static final String UPDATE_PACIENTE = "UPDATE public.paciente SET nombre= ?, ci = ?, edad = ?, estado = ?  WHERE id = ?";
    private static final String SELECT_PACIENTE_BASE = "SELECT * FROM public.paciente";
    private static final String SELECT_PACIENTE_BY_ESTADO = SELECT_PACIENTE_BASE+ " WHERE (? IS NULL OR ESTADO = ?)";
    private static final String SELECT_PACIENTE_BY_ID = SELECT_PACIENTE_BASE + " WHERE id = ?";
    private static final String SELECT_PACIENTE_BY_CI_AND_STATUS = SELECT_PACIENTE_BASE + " WHERE ci = ? and (? IS NULL OR estado = ?)";

    //SIGNOS VITALES SQL
    private static final String INSERT_SIGNO_VITAL = "INSERT INTO public.signos_vitales (id_paciente, presion_arterial_sistolica, presion_arterial_diastolica, frecuencia_cardiaca) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SIGNO_VITAL = "UPDATE public.signos_vitales SET presion_arterial_sistolica = ?, presion_arterial_diastolica = ?, frecuencia_cardiaca = ? WHERE id = ?";
    private static final String DELETE_SIGNO_VITAL = "DELETE FROM public.signos_vitales WHERE id = ?";
    private static final String SELECT_SIGNO_VITAL = "SELECT * FROM public.signos_vitales WHERE id = ?";
    private static final String SELECT_SIGNOS_VITALES_POR_PACIENTE = "SELECT * FROM public.signos_vitales WHERE id_paciente = ?";

    //RANGO_PRESION_ARTERIAL Y FREC CARDIACA
    private static final String SELECT_RANGOS_PRESION_ARTERIAL = "SELECT * FROM public.rango_presion_arterial_normal";
    private static final String SELECT_RANGOS_FRECUENCIA_CARDIACA = "SELECT * FROM public.rango_frecuencia_cardiaca_normal";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Paciente createPaciente(PacienteReq req) throws APIException{
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_PACIENTE, new String[]{"id"});
            ps.setString(1, req.getNombre());
            ps.setString(2, req.getCi());
            ps.setInt(3, req.getEdad());
            return ps;
        }, holder);

        int newUserId = Objects.requireNonNull(holder.getKey()).intValue();
        return obtenerPacientePorId(newUserId);
    }

    @Override
    public Paciente actualizarPaciente(Integer id, PacienteReq req) throws APIException{
        Object[] params = {req.getNombre(),req.getCi(),req.getEdad(), req.getEstado(), id};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER};
        try {
            int update = jdbcTemplate.update(UPDATE_PACIENTE,params,types);
            if(update>0)
                return obtenerPacientePorId(id);
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }

        return null;
    }

    @Override
    public List<Paciente> obtenerPacientes(Estado estado) throws APIException{
        Object[] params = new Object[]{null, null};

        if(estado!=null)
            params = new Object[]{estado.name(), estado.name()};

        int[] types = {Types.VARCHAR, Types.VARCHAR};
        try {
            return jdbcTemplate.query(SELECT_PACIENTE_BY_ESTADO, params, types, new PacienteRowMapper());
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }
    }

    @Override
    public Paciente obtenerPacientePorCi(String ci, Estado estado) throws APIException{
        Object[] params = {ci,null, null};
        if(estado!=null)
            params = new Object[]{ci,estado.name(), estado.name()};

        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
        return getPaciente(params, types, SELECT_PACIENTE_BY_CI_AND_STATUS);

    }

    private Paciente getPaciente(Object[] params, int[] types, String selectPacienteByCiAndStatus) throws APIException {
        try {
            return jdbcTemplate.queryForObject(selectPacienteByCiAndStatus,params,types, new PacienteRowMapper());
        }catch (EmptyResultDataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.APPLICATION, ErrorCode.ERROR_PACIENTE_NOT_EXIST, ErrorCode.ERROR_MESSAGE_PACIENTE_NOT_EXIST, true);
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }
    }

    @Override
    public Paciente obtenerPacientePorId(Integer id) throws APIException{
        Object[] params = {id};
        int[] types = {Types.INTEGER};
        return getPaciente(params, types, SELECT_PACIENTE_BY_ID);

    }

//SIGNOS VITALES

    @Override
    public List<SignosVitales> obtenerSignosVitalesPorPaciente(Integer idPaciente) throws APIException {
        Object[] params = {idPaciente};
        int[] types = {Types.INTEGER};
        try {
            return jdbcTemplate.query(SELECT_SIGNOS_VITALES_POR_PACIENTE,params,types, new SignosVitalesSinPacienteRowMapper());
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }
    }

    @Override
    public SignosVitales obtenerSignosVitalesPorId(Integer id) throws APIException {
        Object[] params = {id};
        int[] types = {Types.INTEGER};
        try {
            return jdbcTemplate.queryForObject(SELECT_SIGNO_VITAL,params,types, new SignosVitalesRowMapper());
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }
    }

    @Override
    public SignosVitales insertSignosVitales(Integer idPaciente, SignosVitalesReq req) throws APIException {
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SIGNO_VITAL, new String[]{"id"});
            ps.setInt(1, idPaciente);
            ps.setInt(2, req.getSistolica());
            ps.setInt(3, req.getDiastolica());
            ps.setInt(4, req.getFrecuenciaCardiaca());
            return ps;
        }, holder);

        int newUserId = Objects.requireNonNull(holder.getKey()).intValue();
        return obtenerSignosVitalesPorId(newUserId);
    }

    @Override
    public SignosVitales updateSignosVitales(Integer id, SignosVitalesReq req) throws APIException {
        Object[] params = {req.getSistolica(),req.getDiastolica(), req.getFrecuenciaCardiaca(), id};
        int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};
        try {
            int update = jdbcTemplate.update(UPDATE_SIGNO_VITAL,params,types);
            if(update>0)
                return obtenerSignosVitalesPorId(id);
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }

        return null;
    }

    @Override
    public void deleteSignosVitales(Integer idSignoVital) throws APIException {
        Object[] params = {idSignoVital};
        int[] types = {Types.INTEGER};
        try {
            jdbcTemplate.update(DELETE_SIGNO_VITAL,params,types);
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }

    }

    @Override
    public List<RangoPresionArterial> obtenerRangosPresionArterial() throws APIException {
        try {
            return jdbcTemplate.query(SELECT_RANGOS_PRESION_ARTERIAL, new RangosPresionArterialRowMapper());
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }
    }

    @Override
    public FrecuenciaCardiacaNormal obtenerFrecuenciaCardiacaNormal() throws APIException {
        try {
            return jdbcTemplate.queryForObject(SELECT_RANGOS_FRECUENCIA_CARDIACA, new FrecuenciaCardiacaNormalRowMapper());
        }catch (DataAccessException e){
            logger.error("Ocurrio un error inesperado",e);
            throw new APIException(APIExceptionType.DATABASE, ErrorCode.ERROR_UNEXPECTED_DB, ErrorCode.ERROR_MESSAGE_DATABASE);
        }
    }

    private class PacienteRowMapper implements RowMapper<Paciente>{
        @Override
        public Paciente mapRow(ResultSet rs, int i) throws SQLException {
            Paciente paciente = new Paciente();
            paciente.setId(rs.getInt("id"));
            paciente.setEdad(rs.getInt("edad"));
            paciente.setCi(rs.getString("ci"));
            paciente.setNombre(rs.getString("nombre"));
            paciente.setEstado(Estado.valueOf(rs.getString("estado")));
            String fechaIngreso = NurseUtil.convertirDateToString(rs.getTimestamp("fecha_registro"));
            paciente.setFechaRegistro(fechaIngreso);
            return paciente;
        }
    }

    private class SignosVitalesRowMapper implements RowMapper<SignosVitales>{
        @Override
        public SignosVitales mapRow(ResultSet rs, int i) throws SQLException {
            SignosVitales signosVitales = new SignosVitales();
            signosVitales.setId(rs.getInt("id"));
            signosVitales.setSistolica(rs.getInt("presion_arterial_sistolica"));
            signosVitales.setDiastolica(rs.getInt("presion_arterial_diastolica"));
            signosVitales.setFrecuenciaCardiaca(rs.getInt("frecuencia_cardiaca"));
            try {
                signosVitales.setPaciente(obtenerPacientePorId(rs.getInt("id_paciente")));
            } catch (APIException e) {
                logger.error("Ocurrio un error al obtener el paciente",e);
            }
            String fechaIngreso = NurseUtil.convertirDateToString(rs.getTimestamp("fecha_registro"));
            signosVitales.setFechaRegistro(fechaIngreso);
            return signosVitales;
        }
    }

    private class SignosVitalesSinPacienteRowMapper implements RowMapper<SignosVitales>{
        @Override
        public SignosVitales mapRow(ResultSet rs, int i) throws SQLException {
            SignosVitales signosVitales = new SignosVitales();
            signosVitales.setId(rs.getInt("id"));
            signosVitales.setSistolica(rs.getInt("presion_arterial_sistolica"));
            signosVitales.setDiastolica(rs.getInt("presion_arterial_diastolica"));
            signosVitales.setFrecuenciaCardiaca(rs.getInt("frecuencia_cardiaca"));
            String fechaIngreso = NurseUtil.convertirDateToString(rs.getTimestamp("fecha_registro"));
            signosVitales.setFechaRegistro(fechaIngreso);
            return signosVitales;
        }
    }

    private class RangosPresionArterialRowMapper implements RowMapper<RangoPresionArterial>{
        @Override
        public RangoPresionArterial mapRow(ResultSet rs, int i) throws SQLException {
            RangoPresionArterial rango = new RangoPresionArterial();
            rango.setId(rs.getInt("id"));
            rango.setCategoria(rs.getString("categoria"));
            rango.setSistolica(rs.getString("sistolica"));
            rango.setDiastolica(rs.getString("diastolica"));
            return rango;
        }
    }

    private class FrecuenciaCardiacaNormalRowMapper implements RowMapper<FrecuenciaCardiacaNormal>{
        @Override
        public FrecuenciaCardiacaNormal mapRow(ResultSet rs, int i) throws SQLException {
            FrecuenciaCardiacaNormal frecuencia = new FrecuenciaCardiacaNormal();
            frecuencia.setRangoMayor(rs.getInt("rango_mayor"));
            frecuencia.setRangoMenor(rs.getInt("rango_menor"));
            return frecuencia;
        }
    }
}
