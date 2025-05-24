package tp1.control;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends ParamsCommand {
	private static final String NAME = Messages.COMMAND_SAVE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_SAVE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_SAVE_DETAILS;
	private static final String HELP = Messages.COMMAND_SAVE_HELP;
	private String fileName;
	private final int allowedParams = 1;
	
	SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	Command parse(String[] commandWords) throws CommandParseException {
		if(this.matchCommands(commandWords[0])) {
			if (commandWords.length < this.allowedParams) 
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			else {
				this.setNumParams(1);
				//Idem (see LoadCommand)
				this.fileName = this.getStringFileName(commandWords);
				return this;
			}
		}
		return null;
	}

	@Override
	void execute(GameModel g, GameView view) throws CommandExecuteException {
		try {
			g.save(this.fileName);
			view.showMessage(Messages.FILE_SAVED.formatted(this.fileName));
		}
		catch(GameModelException gme) {
			throw new CommandExecuteException();
		}
	}
	
	
	/*---PRIVATE---*/
	
	private String getStringFileName(String[] s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < s.length-1; ++i) {
			sb.append(s[i]).append(' ');
		}
		sb.append(s[s.length-1]);
		
		return sb.toString();
	}

}
