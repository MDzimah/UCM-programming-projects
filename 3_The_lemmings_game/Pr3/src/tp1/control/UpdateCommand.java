package tp1.control;

import tp1.exceptions.CommandExecuteException;
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
	
	
	/*---CHECKERS---*/
	
	@Override
	protected boolean matchCommands(String name) {
		//The line separator (empty String) is a valid shortcut for UpdateCommand
		return name.equalsIgnoreCase(SHORTCUT) || name.equalsIgnoreCase(NAME) || name.equals(Messages.EMPTY);
	}
	
	
	/*---OTHERS---*/
	
	@Override
	protected void execute(GameModel g, GameView view) throws CommandExecuteException {
		g.update();
		view.showGame();
	}
}
