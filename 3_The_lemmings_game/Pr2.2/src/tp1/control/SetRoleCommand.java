package tp1.control;

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
	private int row;
	private int col;
	
	
	public SetRoleCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	private int convertLetterToInt(char c) {
		c = Character.toLowerCase(c);
		return c - 'a';
	}
	
	@Override
	public Command parse(String[] commandWords) {
		
		if(this.matchCommands(commandWords[0])) {
			if (commandWords.length > 1) {
				this.lr = LemmingRoleFactory.parse(commandWords[1]);
				
				if (this.lr != null) {
					this.setNumParams(commandWords.length-1);
					
					if(this.getNumParams() == this.allowedParams)
					{
						//Se comprueba que el setRole command tiene sus parámetros fila y columna correctos
						if(commandWords[2].length() == 1 && Character.isLetter(commandWords[2].charAt(0)) 
								&& this.isNumber(commandWords[3])) 
						{
							this.col = this.stringToNumber(commandWords[3])-1;
							this.row = this.convertLetterToInt(commandWords[2].charAt(0));
						}
						else this.col = this.row = -1; //SetRole con parámetros "row" y "col" incorrectos
						return this;
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public void execute(GameModel g, GameView view) {
		Position p = new Position(this.row, this.col);
		
		if(p.isInBoard() && g.setRole(this.lr, p)) {
			g.update();
			view.showGame();
		}
		else view.showError(Messages.COMMAND_INCORRECT_SETROLE);
			
	}
	
	@Override
	protected String helpText() {
		StringBuilder s = new StringBuilder(Messages.LINE_TAB.formatted(Messages.COMMAND_HELP_TEXT.formatted(getDetails(), getHelp())));
		s.append(LemmingRoleFactory.helpText());
		return s.toString();
	}
}
