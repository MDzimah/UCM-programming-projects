package tp1.exceptions;

public class GameParseException extends GameModelException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GameParseException(){ super(); }
	public GameParseException(String message, Throwable cause) { super(message, cause); }
	public GameParseException(String message) { super(message); }
}
