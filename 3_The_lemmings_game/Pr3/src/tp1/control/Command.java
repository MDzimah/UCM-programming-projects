package tp1.control;

import tp1.view.GameView;
import tp1.view.Messages;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;

//Receives the generated command and executes it by filtering it into its subclasses (ResetCommand, HelpCommand, ExitCommand, etc.)
public abstract class Command {
	private final String name;
	private final String shortcut;
	private final String details;
	private final String help;
		
	public Command(String name, String shortcut, String details, String help)
	{
		this.name = name;
		this.shortcut = shortcut;
		this.details = details;
		this.help = help;
	}
	
	
	/*---GETTERS---*/
	
	String helpText() {
		return Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(this.details, this.help));
	}
	
	
	/*---CHECKERS---*/

	boolean matchCommands(String name) {
		return name.equalsIgnoreCase(this.shortcut) || name.equalsIgnoreCase(this.name);
	}
	
	abstract Command parse(String[] sArray) throws CommandParseException;
	
	
	/*---OTHERS---*/
	
	abstract void execute(GameModel g, GameView view) throws CommandExecuteException;
}
