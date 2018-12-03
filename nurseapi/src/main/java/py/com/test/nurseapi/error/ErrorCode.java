package py.com.test.nurseapi.error;

/**
 * @author mlopez
 * @fecha 11/29/18,2:05 PM
 */


public class ErrorCode {

    //GENERALES
    public static final String ERROR_DATABASE_REQUEST = "g1000";
    public static final String ERROR_FORMATO_FECHA_INCORRECTO= "g1010";
    public static final String ERROR_ESTADO_DESCONOCIDO= "g1020";
    public static final String ERROR_INVALID_ISO8601_DATE= "g1050";
    public static final String ERROR_TIPO_IDENTIFICACION_DESCONOCIDO= "g1080";

    //APLICACION
    public static final String ERROR_PACIENTE_NOT_EXIST = "p1000";
    public static final String ERROR_MESSAGE_PACIENTE_NOT_EXIST = "Paciente no existe";

    //MANEJADOR
    public static final String ERROR_MISSING_PARAMETER= "m1000";
    public static final String ERROR_MISSING_INVALID_BODY_PARAMETER="m1010";
    public static final String ERROR_HTTP_INVALID_METHOD="m1030";
    public static final String ERROR_UNEXPECTED_WEB="m1040";
    public static final String ERROR_UNEXPECTED_DB="m1050";
    public static final String ERROR_UNEXPECTED="m2000";

    //message
    public static final String ERROR_MESSAGE_DATABASE = "Ocurrio un error inesperado con la BD";


}