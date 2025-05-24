package tp1.control;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameModel;
import tp1.logic.lemmingsRole.LemmingRole;
import tp1.logic.lemmingsRole.LemmingRoleFactory;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.logic.Position;

public class SetRoleCommand extends ParamsCommand {
	private static final String NAME = Messages.COMMAND_SET_ROLE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_SET_ROLE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_SET_ROLE_DETAILS;
	private static final String HELP = Messages.COMMAND_SET_ROLE_HELP;
	private LemmingRole lr = null;
	private final int allowedParams = 3;
	private int numExtraParams = 0;
	private int row;
	private int col;
	
	
	SetRoleCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	private int convertLetterToInt(char c_upperCase) {
		return c_upperCase - 'A'; //Specification requires upper case letter when giving the position
	}
	
	@Override
	Command parse(String[] commandWords) throws CommandParseException {
		if(this.matchCommands(commandWords[0])) {
			
			//Checking whether SetRoleCommand has any parameters at all
			if (commandWords.length > 1) {
				this.lr = LemmingRoleFactory.parse(commandWords[1]);
								
				//Checking whether it has a valid role
				if (this.lr != null) {
					StringBuilder extraParams = new StringBuilder();
					
					//For roles with extra parameters
					if (commandWords.length > 4) { 
						extraParams.append(commandWords[2]); ++this.numExtraParams;
						for (int i = 3; i < commandWords.length - 2; ++i) {
							extraParams.append(' ').append(commandWords[i]);
							++this.numExtraParams;
						}
					}
					
					//If it has extra parameters, checking whether they are valid (if it doesn't have extra parameters, true by default)
					if (this.lr.parseExtraParams(extraParams.toString(), this)) {
						this.setNumParams(commandWords.length-1);
						
						//Checking whether it has the appropriate number of parameters
						if(this.getNumParams() == this.allowedParams + this.numExtraParams)
						{
							//Checking whether it has valid positional parameters
							if(commandWords[2 + this.numExtraParams].length() == 1 && Character.isLetter(commandWords[2 + this.numExtraParams].charAt(0))) 
							{
								try {
								this.col = stringToNumber(commandWords[3 + this.numExtraParams])-1;
								this.row = this.convertLetterToInt(commandWords[2 + this.numExtraParams].charAt(0));
								}
								catch(NumberFormatException e) {
									throw new CommandParseException(Messages.INVALID_POSITION); 
								}
							}
							return this;
						}
						else throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
					}
					else throw new CommandParseException (Messages.INVALID_PARAMETERS);
				}
				else throw new CommandParseException (Messages.INVALID_PARAMETERS, new CommandParseException(Messages.UNKNOWN_ROLE.formatted(commandWords[1])));
			}
		}
		return null;
	}

	
	@Override
	protected void execute(GameModel g, GameView view) throws CommandExecuteException {
		Position p = new Position(this.row, this.col);
		
		try {
			if(p.isInBoard()) {
				try {
					if (g.setRole(this.lr, p)) {
						g.update();
						view.showGame();
					}
				}
				catch(OffBoardException obe) {
					throw new CommandExecuteException(obe.getMessage()); //Lemming in position "p" doesn't admit said role
				}
			}
		}
		catch (OffBoardException obe) {
			throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, obe);
		}
	}
	
	@Override
	protected String helpText() {
		StringBuilder s = new StringBuilder(Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(DETAILS, HELP)));
		s.append(LemmingRoleFactory.helpText());
		return s.toString();
	}
	
	public void resetNumExtraCommands() { this.numExtraParams = 0; }
}
