package tp1.control;

import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends NoParamsCommand{
	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;
	
	public ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	public void execute(GameModel g, GameView view) {
		g.reset();
		view.showGame();
	}
}
