package tp1.control;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;

class ResetCommand extends ParamsCommand {
	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;
	private final int allowedParams = 1;
	private int level;
	
	
	ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	
	/*---CHECKERS---*/
	
	Command parse(String[] commandWords) throws CommandParseException {
		if(this.matchCommands(commandWords[0])) {
			if (commandWords.length == 1) {
				//ResetCommand without parameters (valid)
				this.setNumParams(0);
				return this; 
			}
			else {
				try {
					this.level = this.stringToNumber(commandWords[1]);
					this.setNumParams(commandWords.length-1); //No need to include "reset" as a parameter
					
					if (this.getNumParams() > this.allowedParams)
						throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER); //ResetCommand with more than one parameter (not valid)
					else return this; //ResetCommand with 1 parameter (int) that can be valid or not
				}
				catch (NumberFormatException e) { return null; } //ResetCommand but with a non-numeric parameter (Unknown command error per the specification)
			} 
		}
		else return null;
	}
	
	
	/*---OTHERS---*/
	
	@Override
	void execute(GameModel g, GameView view) throws CommandExecuteException{
		if(this.getNumParams() == 0) {
			g.reset(g.getLevel()); 
			view.showGame();
		}
		else if (this.level < 0 || this.level >= LevelGameConfiguration.numberOfLevels) throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER);
		else {
			g.reset(this.level);
			view.showGame();
		}
	}
}
