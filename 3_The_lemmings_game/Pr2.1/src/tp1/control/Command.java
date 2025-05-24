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
		//Como el comando "UPDATE" no puede tener 2 shortcuts a la vez ('\n', 'n'), metiendo aquí una condición de más para capturar el salto de línea (= empty)
		//Esto funciona porque "UPDATE" es el primer command de la lista, entonces el caso que sea '\n' llama a update  
		return getShortcut().equalsIgnoreCase(name) || getName().equalsIgnoreCase(name) || name.equals(Messages.EMPTY);
	}
	
	protected abstract Command parse(String[] sArray);
	public abstract void execute(GameModel g, GameView view);
	
	public String helpText() {
		return Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(getDetails(), getHelp()));
	}
	
	protected String toString (String[] s) {
		StringBuilder sb = new StringBuilder(); 
		
		for (String str : s) { 
		    sb.append(str).append(Messages.EMPTY);
		} 
		 
		return sb.toString();
	}
}
