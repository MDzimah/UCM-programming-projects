package tp1.control;

import tp1.logic.Game;
import tp1.view.ConsoleColorsView;
import tp1.view.ConsoleView;
import tp1.view.GameView;
import tp1.view.Messages;
/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private Game game;
	private GameView view;

	public Controller(Game game, GameView view) {
		this.game = game;
		this.view = view;
	}
	
	
	private String convertStringArrayToString (String[] s) {
		StringBuilder sb = new StringBuilder(); 
		
		for (String str : s) { 
		    sb.append(str).append(Messages.EMPTY);
		} 
		 
		return sb.toString();
	}
	
	private void startGame() {
		switch(this.game.getLevel()) {
		case 0: 
		case 1: 
			this.game.level0or1();
			break;
		case 2: this.game.level2();
			break;
		}
	}
	
	public void run() {
		this.view.showWelcome();
		this.startGame();
		this.view.showGame();
		
		boolean exit = false;
		while (!exit && !this.game.isFinished()) {
			
			switch (this.convertStringArrayToString(view.getPrompt()).toLowerCase()) {
				case Messages.EMPTY:
				case Messages.COMMAND_NONE_NAME:
				case Messages.COMMAND_NONE_SHORTCUT: 
					this.game.update();
					this.view.showGame();
					break;
				case Messages.COMMAND_RESET_NAME:
				case Messages.COMMAND_RESET_SHORTCUT: 
					this.game = new Game(this.game.getLevel());
					this.startGame();
					this.view = new ConsoleView(this.game);
					this.view.showGame();
					break;
				case Messages.COMMAND_HELP_NAME:
				case Messages.COMMAND_HELP_SHORTCUT: 
					this.view.showMessage(Messages.HELP);
					break;
				case Messages.COMMAND_EXIT_NAME: 
				case Messages.COMMAND_EXIT_SHORTCUT: 
					exit = true;
					break;
				default:
					this.view.showError(Messages.UNKNOWN_COMMAND);
					break;
			}
		}
		this.view.showEndMessage();
	}

}
