package py.com.test.nurseapi.error;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mlopez
 * @fecha 11/29/18,12:45 PM
 */


public class ErrorInfo {

    @JsonProperty("message")
    private String message;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("uri")
    private String uriRequested;

    public ErrorInfo(int statusCode, String message, String uriRequested) {
        this.message = message;
        this.statusCode = statusCode;
        this.uriRequested = uriRequested;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getUriRequested() {
        return uriRequested;
    }

}