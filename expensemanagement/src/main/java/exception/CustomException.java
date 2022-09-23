package exception;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomException(String errorMessage) throws Exception {
		throw new Exception(errorMessage);
	}

}
