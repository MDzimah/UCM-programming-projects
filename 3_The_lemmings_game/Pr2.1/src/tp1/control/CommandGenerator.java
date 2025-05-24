package tp1.control;

import java.util.Arrays;
import java.util.List;

import tp1.view.Messages;

public class CommandGenerator {
	
	private static final List<Command> available_commands = Arrays.asList(
			new UpdateCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand()
			//
			);
			
	public static Command parse(String[] commandWords) {
		
		for (Command c: available_commands) {
			if (c.parse(commandWords) != null)
				return c;
		}
		
		return null;
	}
	
	public static String commandHelp() {
		StringBuilder s = new StringBuilder(Messages.HELP_AVAILABLE_COMMANDS + '\n');
		
		for (Command c: available_commands) {
			s = s.append(c.helpText());
		} 
		return s.toString();
	}
	

}
