package tp1.control;

import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class UpdateCommand extends NoParamsCommand{
	private static final String NAME = Messages.COMMAND_UPDATE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
	private static final String HELP = Messages.COMMAND_UPDATE_HELP;
	
	public UpdateCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	public void execute(GameModel g, GameView view) {
		g.update();
		view.showGame();
	}
}
