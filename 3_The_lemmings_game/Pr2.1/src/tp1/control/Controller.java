package tp1.control;

import tp1.logic.*;
import tp1.view.GameView;
import tp1.view.Messages;
/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) {
		this.game = game;
		this.view = view;
	}
	
	//Para convertir String[] a String (sobrecarga del String toString())
	private String toString (String[] s) {
		StringBuilder sb = new StringBuilder(); 
		
		for (String str : s) { 
		    sb.append(str).append(Messages.EMPTY);
		} 
		 
		return sb.toString();
	}
		
	public void run() {
		this.view.showWelcome();
		this.view.showGame();
		
		while (!this.game.isFinished()) {
			
			String[] userWords = this.view.getPrompt();
			Command com = CommandGenerator.parse(userWords);

			if (com != null)
				com.execute(this.game, this.view);
			else
				this.view.showError(this.toString(userWords));
		
		}
		this.view.showEndMessage();
	}

}
