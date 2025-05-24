package tp1.exceptions;

public class CommandParseException extends CommandException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CommandParseException(){ super(); }
	public CommandParseException(String message) { super(message); }
	public CommandParseException(String message, Throwable cause) { super(message, cause); }
}
