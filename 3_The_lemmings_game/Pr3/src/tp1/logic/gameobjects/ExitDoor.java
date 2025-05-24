package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject{ 
	public ExitDoor() { super(); }
	public ExitDoor(Position p, GameWorld g) { super(p, g); }
	
	
	/*---GETTERS---*/
	
	@Override
	public String getIcon() { return Messages.EXIT_DOOR; }	
	
	
	/*---CHECKERS---*/
	
	@Override
	public boolean matchesGObjName(String s) { 
		return s.equalsIgnoreCase(Messages.EXIT_DOOR_NAME) 
				|| s.equalsIgnoreCase(Messages.EXIT_DOOR_SHORTCUT); 
	}
	
	@Override
	public boolean isSolid() { return false; }
	
	
	/*---INTERACTIONS---*/
	
	@Override
	public void receiveInteraction(GameItem other) { other.interactWith(this); }
	
	
	/*---OTHERS---*/
	
	public String toString() {
		return this.createPosition(Direction.NONE).toString() + ' ' + Messages.EXIT_DOOR_NAME;
	}
	
	@Override
	protected GameObject createNewObject (Position p, GameWorld g) { 
		return new ExitDoor(p, g);
	}
}
