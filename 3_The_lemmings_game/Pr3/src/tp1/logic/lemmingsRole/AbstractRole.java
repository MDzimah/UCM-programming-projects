package tp1.logic.lemmingsRole;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;

//To generalize trivial interactions such as wall-wall that don't do anything
public abstract class AbstractRole {
	public void interactionWith(Lemming receiver, Lemming owner) {}
	public void interactionWith(Wall w, Lemming owner) {}
	public void interactionWith(ExitDoor ed, Lemming owner) {}
	public void interactionWith(MetalWall mW, Lemming owner) {}
}
