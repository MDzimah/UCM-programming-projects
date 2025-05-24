package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class MetalWall extends GameObject {
	public MetalWall() { super(); }
	public MetalWall(Position p, GameWorld g) { super(p, g); }
	
	
	/*---GETTERS---*/
	
	@Override
	public String getIcon() { return Messages.METALWALL; }
	
	
	/*---CHECKERS---*/
	
	@Override
	public boolean matchesGObjName(String s) { 
		return s.equalsIgnoreCase(Messages.METALWALL_NAME) 
				|| s.equalsIgnoreCase(Messages.METALWALL_SHORTCUT); 
	}
	
	
	/*---INTERACTIONS---*/
	
	@Override
	public void receiveInteraction(GameItem other) { other.interactWith(this); }

	
	/*---OTHERS---*/
	
	@Override
	public String toString() {
		return this.createPosition(Direction.NONE).toString() + ' ' + Messages.METALWALL_NAME;
	}
	
	@Override
	protected GameObject createNewObject(Position p, GameWorld g) { 
		return new MetalWall(p, g);
	}
}
