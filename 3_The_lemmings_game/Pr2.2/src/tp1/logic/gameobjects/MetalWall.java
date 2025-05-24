package tp1.logic.gameobjects;

import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class MetalWall extends GameObject {

	public MetalWall(Position p, GameWorld g) { super(p, g); }
	
	
	/*---GETTERS---*/
	
	@Override
	public String getIcon() { return Messages.METALWALL; }
	
	
	/*---INTERACTIONS---*/
	
	@Override
	public void receiveInteraction(GameItem other) { other.interactWith(this); }
}
