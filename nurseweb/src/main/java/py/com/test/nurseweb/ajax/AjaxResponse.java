package py.com.test.nurseweb.ajax;

/**
 * @author mlopez
 * @fecha 11/20/18,6:49 PM
 */


public class AjaxResponse {
    private boolean hasError;
    private Object errorBody;
    private Object respuesta;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Object getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(Object errorBody) {
        this.errorBody = errorBody;
    }

    public Object getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Object respuesta) {
        this.respuesta = respuesta;
    }

    public AjaxResponse(boolean hasError, Object errorBody) {
        this.hasError = hasError;
        this.errorBody = errorBody;
    }

    public AjaxResponse(Object respuesta) {
        this.respuesta = respuesta;
        this.hasError = false;
    }

    public AjaxResponse() {
    }
}
