package tp1.logic.gameobjects;

import tp1.logic.Position;

public interface GameItem {
	public void receiveInteraction(GameItem other);
	public void interactWith(Lemming l);
	public void interactWith(Wall w);
	public void interactWith(ExitDoor ed);
	public void interactWith(MetalWall mW);
	
	public boolean isInPosition(Position p);
}
