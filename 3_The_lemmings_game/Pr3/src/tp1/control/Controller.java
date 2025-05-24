package tp1.control;

import tp1.exceptions.CommandException;
import tp1.logic.*;
import tp1.view.GameView;

//Accepts user input and coordinates the game execution logic
public class Controller {
	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) {
		this.game = game;
		this.view = view;
	}
		
	public void run(){
		this.view.showWelcome();
		this.view.showGame();
		
		while (!this.game.isFinished()) {
			
			String[] userWords = this.view.getPrompt();
			
			try {
				Command com = CommandGenerator.parse(userWords);
				com.execute(this.game, this.view);
			} 
			catch (CommandException e) {
				Throwable cause = e.getCause();
				if (cause != null) {
					this.view.showErrorWithoutLineSeparator(e.getMessage());
					this.view.showError(cause.getMessage());
				}
				else this.view.showError(e.getMessage());
			}
		}
		this.view.showEndMessage();
	}

}
