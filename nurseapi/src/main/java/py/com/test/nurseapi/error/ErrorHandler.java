package py.com.test.nurseapi.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import py.com.test.nurseapi.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mlopez
 * @fecha 11/29/18,1:35 PM
 */

@ControllerAdvice
public class ErrorHandler {

    private static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(value = Throwable.class)
    public @ResponseBody
    ErrorBody handleGlobalException(HttpServletRequest request, HttpServletResponse response, Throwable e)
    {
        logger.error("Manejando error", e);
        request.setAttribute(Constants.ATTRIBUTE_EXCEPTION, e);
        ErrorBody eb = new ErrorBody();
        eb.setUseApiMessage(false); //todos son false, el unico caso que puede ser true es si del tipo APIException lo sobreescribe
        boolean statusSet = false;

        if (e instanceof APIException)
        {
            APIException ae = (APIException)e;
            eb.setCode(ae.getCode());
            eb.setMessage(ae.getMessage());
            eb.setType(ae.getType());
            eb.setUseApiMessage(ae.getUseApiMessage());
            // MissingServletRequestParameterException
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException msrpe = (MissingServletRequestParameterException)e;
            eb.setCode(ErrorCode.ERROR_MISSING_PARAMETER);
            eb.setMessage(String.format("Parametro [%s] de tipo [%s] no encontrado en el request", msrpe.getParameterName(), msrpe.getParameterType()));
            eb.setType(APIExceptionType.APPLICATION);
        }else if(e instanceof HttpMessageNotReadableException){
//				HttpMessageNotReadableException mnre = (HttpMessageNotReadableException)e;
            eb.setCode(ErrorCode.ERROR_MISSING_INVALID_BODY_PARAMETER);
            eb.setMessage("Parametro del body invalido o inexistente");
            eb.setType(APIExceptionType.APPLICATION);

        } else if (e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException)e;
            BindingResult result = manve.getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();

            // convertimos a un String entendible
            StringBuilder errorMessage = new StringBuilder();
            fieldErrors.forEach(f -> errorMessage.append(f.getField()).append(" ").append(f.getDefaultMessage()).append(" "));

            eb.setCode(ErrorCode.ERROR_MISSING_PARAMETER);
            eb.setMessage(errorMessage.toString());
            eb.setType(APIExceptionType.APPLICATION);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException mnse = (HttpRequestMethodNotSupportedException)e;
            eb.setCode(ErrorCode.ERROR_HTTP_INVALID_METHOD);
            eb.setMessage(String.format("Metodo [%s] no es soportado para este servicio", mnse.getMethod()));
            eb.setType(APIExceptionType.INTERNAL);
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            statusSet = true;
        } else if (e instanceof NoHandlerFoundException) {
            NoHandlerFoundException nhfe = (NoHandlerFoundException)e;
            eb.setCode(ErrorCode.ERROR_HTTP_INVALID_METHOD);
            eb.setMessage(String.format("handler no encontrado para url [%s] y metodo [%s]", nhfe.getRequestURL(), nhfe.getHttpMethod()));
            eb.setType(APIExceptionType.APPLICATION);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            statusSet = true;
        } else if (e instanceof HttpMediaTypeException) {
//				HttpMediaTypeException mte = (HttpMediaTypeException)e;
            eb.setCode(ErrorCode.ERROR_UNEXPECTED_WEB);
            eb.setMessage("Unexpected WEB COMPONENT error");
            eb.setType(APIExceptionType.INTERNAL);
        } else if (e instanceof DataAccessException) {
//				DataAccessException dae = (DataAccessException)e;
            eb.setCode(ErrorCode.ERROR_UNEXPECTED_DB);
            eb.setMessage("Error DATABASE inesperado");
            eb.setType(APIExceptionType.DATABASE);
        } else if (e instanceof SQLException) {
//				SQLException se = (SQLException)e;
            eb.setCode(ErrorCode.ERROR_UNEXPECTED_DB);
            eb.setMessage("Error DATABASE inesperado");
            eb.setType(APIExceptionType.DATABASE);
        } else {
            eb.setCode(ErrorCode.ERROR_UNEXPECTED);
            eb.setMessage("Unexpected server exception");
            eb.setType(APIExceptionType.INTERNAL);
        }

        if (!statusSet) {
            switch(eb.getType())
            {
                case APPLICATION:
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    break;
                case SECURITY:
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    break;
                case COMMUNICATION:
                case DATABASE:
                case INTERNAL:
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    break;
            }
        }

        return eb;
    }

}
