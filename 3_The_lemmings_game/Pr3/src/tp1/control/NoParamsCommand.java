package tp1.control;

import tp1.exceptions.CommandParseException;
import tp1.view.Messages;

public abstract class NoParamsCommand extends Command {
	NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	@Override
	Command parse(String[] commandWords) throws CommandParseException {
		if(this.matchCommands(commandWords[0])) {
			if (commandWords.length <= 1) return this; //<= 1 due to shortcut being also '\n'
			else throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		return null;
	}
	
	String toString (String[] s) {
		StringBuilder sb = new StringBuilder(); 
		
		for (String str : s) { 
		    sb.append(str).append(Messages.EMPTY);
		} 
		 
		return sb.toString();
	}
}
