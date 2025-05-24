package tp1.exceptions;

public class CommandExecuteException extends CommandException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CommandExecuteException(){ super(); }
	public CommandExecuteException(String message){ super(message); }
	public CommandExecuteException(String message, Throwable cause) { super(message, cause); }
}
