package tp1.control;

import tp1.view.Messages;

public abstract class NoParamsCommand extends Command {
	protected NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	@Override
	protected Command parse(String[] commandWords) {
		if (this.matchCommands(this.toString(commandWords))) {
			return this;
		}
		else return null;
	}
	
	protected String toString (String[] s) {
		StringBuilder sb = new StringBuilder(); 
		
		for (String str : s) { 
		    sb.append(str).append(Messages.EMPTY);
		} 
		 
		return sb.toString();
	}
}
