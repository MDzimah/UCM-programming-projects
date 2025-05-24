package tp1.control;

import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class UpdateCommand extends NoParamsCommand{
	private static final String NAME = Messages.COMMAND_UPDATE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
	private static final String HELP = Messages.COMMAND_UPDATE_HELP;
	
	protected UpdateCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	protected boolean matchCommands(String name) {
		//Como el comando "UPDATE" no puede tener 2 shortcuts a la vez ('\n', 'n'), metiendo aquí una condición de más para capturar el salto de línea (= empty)
		//Esto funciona porque "UPDATE" es el primer command de la lista, entonces el caso que sea '\n' llama a update  
		return name.equalsIgnoreCase(getShortcut()) || name.equalsIgnoreCase(getName()) || name.equals(Messages.EMPTY);
	}
	
	@Override
	public void execute(GameModel g, GameView view) {
		g.update();
		view.showGame();
	}
}
