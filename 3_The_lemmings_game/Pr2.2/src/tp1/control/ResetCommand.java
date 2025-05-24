package tp1.control;

import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends ParamsCommand {
	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;
	private final int allowedParams = 1;
	private int level;
	
	
	protected ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}


	protected Command parse(String[] commandWords) {
		if(this.matchCommands(commandWords[0])) {
			if (commandWords.length == 1) {
				//Command reset sin parámetros (es válido)
				this.setNumParams(0);
				return this; 
			}
			else if(this.isNumber(commandWords[1])) {
				
				this.setNumParams(commandWords.length-1); //Se excluye la instrucción propia de "reset"
				
				if (this.getNumParams() == this.allowedParams) {
					//Command reset con 1 parámetro de tipo int (puede ser válido o no)
					this.level = this.stringToNumber(commandWords[1]);
				}
				//(else) Command reset con más de 1 parámetro (no es válido)
				
				return this;
			}
			else return null; //Command reset pero con el parámetro no numérico (Unknown command error)
		}
		else return null;
	}
	
	@Override
	public void execute(GameModel g, GameView view) {
		if (this.getNumParams() > this.allowedParams) view.showError(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		else { 
			if(this.getNumParams() == 0) {
				g.reset(g.getLevel()); 
				view.showGame();
			}
			else if (this.level < 0 || this.level >= g.getNumLevels()) view.showError(Messages.INVALID_LEVEL_NUMBER);
			else {
				g.reset(this.level);
				view.showGame();
			}
		}
	}
}
