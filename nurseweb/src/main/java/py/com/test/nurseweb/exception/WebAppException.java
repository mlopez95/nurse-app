package py.com.test.nurseweb.exception;

public class WebAppException extends Exception {

	private static final long serialVersionUID = 1L;
	private ErrorData errorData;

	public WebAppException(ErrorData errorBody) {
		this.errorData = errorBody;
	}

	public WebAppException(String msg) {
		ErrorData errorData = new ErrorData();
		errorData.setCode("200018");
		errorData.setDescription(msg);
		errorData.setType(ErrorData.WebErrorSeverity.ERROR.name());
		errorData.setUserMessage(msg);
		this.errorData = errorData;
	}

	public ErrorData getErrorBody() {
		return errorData;
	}

	public void setErrorBody(ErrorData errorBody) {
		this.errorData = errorBody;
	}
}
