package tp1.logic.lemmingsRole;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;

public interface LemmingRole {
	public abstract void start(Lemming l);
	public abstract LemmingRole parse(String s);
	
	public abstract String getHelp();	
	public abstract String getIcon(Lemming l);
	public abstract String getSymbol();
	
	public abstract void resetInteraction(Lemming l);
	public abstract void receiveInteraction(GameItem other, Lemming owner);
	public abstract void interactionWith(Lemming receiver, Lemming owner);
	public abstract void interactionWith(Wall w, Lemming owner);
	public abstract void interactionWith(ExitDoor ed, Lemming owner);
	public abstract void interactionWith(MetalWall mW, Lemming owner);
}
