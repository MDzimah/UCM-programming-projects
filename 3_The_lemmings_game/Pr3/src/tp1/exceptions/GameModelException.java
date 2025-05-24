package tp1.exceptions;

public class GameModelException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GameModelException(){ super(); }
	public GameModelException(String message) { super(message); }
	public GameModelException(String message, Throwable cause) { super(message, cause); }
}
