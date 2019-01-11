package li.nicetry.app.exception;


public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -6334411641057809807L;

	private ErrorObject error;

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(ErrorObject error) {
		super();
		this.error = error;
	}

	public ErrorObject getError() {
		return error;
	}

	public void setError(ErrorObject error) {
		this.error = error;
	}

}
