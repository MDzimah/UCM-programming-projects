package tp1.logic.gameobjects;

import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject{
	
	public ExitDoor(Position p, GameWorld g) { super(p, g); }
	
	
	/*---GETTERS---*/
	
	@Override
	public String getIcon() { return Messages.EXIT_DOOR; }	
	
	
	/*---CHECKERS---*/
	
	@Override
	public boolean isSolid() { return false; }
	
	
	/*---INTERACTIONS---*/
	
	@Override
	public void receiveInteraction(GameItem other) { other.interactWith(this); }
}
