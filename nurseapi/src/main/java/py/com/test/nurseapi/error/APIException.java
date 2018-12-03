package py.com.test.nurseapi.error;

/**
 * @author mlopez
 * @fecha 11/29/18,2:05 PM
 */

public class APIException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private APIExceptionType type;
    private String code;
    private Boolean useApiMessage;

    public APIException(APIExceptionType type, String code, String message,Boolean useApiMessage, Throwable cause)
    {
        super(message, cause);
        setType(type);
        setCode(code);
        setUseApiMessage(useApiMessage);
    }

    public APIException(APIExceptionType type, String code, String message, Throwable cause)
    {
        this(type, code, message,false, cause);
    }

    public APIException(APIExceptionType type, String code, String message)
    {
        this(type, code, message,false, null);
    }

    public APIException(APIExceptionType type, String code, String message,Boolean useApiMessage)
    {
        this(type, code, message,useApiMessage, null);
    }

    public APIException(APIExceptionType type, String code)
    {
        this(type, code, null, false, null);
    }

    public APIException(APIExceptionType type, String code,Boolean useApiMessage)
    {
        this(type, code, null, useApiMessage, null);
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public APIExceptionType getType() {
        return type;
    }
    public void setType(APIExceptionType type) {
        this.type = type;
    }

    public Boolean getUseApiMessage() {
        return useApiMessage;
    }

    public void setUseApiMessage(Boolean useApiMessage) {
        this.useApiMessage = useApiMessage;
    }

}

