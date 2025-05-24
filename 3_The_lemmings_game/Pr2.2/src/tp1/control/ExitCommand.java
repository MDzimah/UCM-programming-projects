package tp1.control;

import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class ExitCommand extends NoParamsCommand{
	private static final String NAME = Messages.COMMAND_EXIT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_EXIT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_EXIT_DETAILS;
	private static final String HELP = Messages.COMMAND_EXIT_HELP;
	
	protected ExitCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	public void execute(GameModel g, GameView view) {
		g.exit();
	}
}
