package tp1.logic.gameobjects;

import tp1.logic.Position;

//Due to Java not allowing in time double-dispatch, this class is required for it
public interface GameItem {
	/*---CHECKERS---*/
	
	public boolean isInPosition(Position p);
	
	
	/*---INTERACTIONS---*/
	
	public void receiveInteraction(GameItem other);
	public void interactWith(Lemming l);
	public void interactWith(Wall w);
	public void interactWith(ExitDoor ed);
	public void interactWith(MetalWall mW);
}
