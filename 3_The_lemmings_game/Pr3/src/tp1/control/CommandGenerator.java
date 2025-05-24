package tp1.control;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.CommandParseException;
import tp1.view.Messages;

//The command given by the user comes here. If it exits, it is created
public class CommandGenerator {
	private static final List<Command> available_commands = Arrays.asList(
			new SetRoleCommand(),
			new UpdateCommand(),
			new ResetCommand(),
			new LoadCommand(),
			new SaveCommand(),
			new HelpCommand(),
			new ExitCommand()
			);
	
	
	/*---GETTERS---*/
	
	static String commandHelp() {
		StringBuilder s = new StringBuilder(Messages.HELP_AVAILABLE_COMMANDS + Messages.LINE_SEPARATOR);
		
		for (Command c: available_commands) {
			s = s.append(c.helpText());
		} 
		return s.toString();
	}
	
	
	/*---CHECKERS---*/
	
	static Command parse(String[] commandWords) throws CommandParseException {
		
		for (Command c: available_commands) {
			if (c.parse(commandWords) != null)
				return c;
		}
		
		throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(commandWords[0]));
	}
}
