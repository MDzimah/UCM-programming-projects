package tp1.control;

import tp1.view.GameView;
import tp1.view.Messages;
import tp1.logic.GameModel;


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
	
	protected String getName() { return name; }

	protected String getShortcut() { return shortcut; }

	protected String getDetails() { return details; }

	protected String getHelp() { return help; }

	protected boolean matchCommands(String name) {
		return name.equalsIgnoreCase(getShortcut()) || name.equalsIgnoreCase(getName());
	}
	
	protected abstract Command parse(String[] sArray);
	public abstract void execute(GameModel g, GameView view);
	
	protected String helpText() {
		return Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(getDetails(), getHelp()));
	}
}
