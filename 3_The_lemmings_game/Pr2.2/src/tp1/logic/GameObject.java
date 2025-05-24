package tp1.logic;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;
import tp1.logic.lemmingsRole.LemmingRole;

public abstract class GameObject implements GameItem {
	private Position pos;
	private GameWorld g;
	private boolean alive;
	
	public GameObject (Position p, GameWorld g)
	{
		this.pos = p;
		this.g = g;
		this.alive = true;
	}
	
	/*---GETTERS---*/
	
	public Position createPosition(Direction d) { Position p = new Position(this.pos, d); return p; }
	
	
	/*---SETTERS---*/
	
	public boolean setRole(LemmingRole r) { return false; }
	
	public void dead() { this.alive = false; }
	
	protected abstract String getIcon();
	
	protected void changePosition(Position p) { this.pos = p; }
	
	protected void increaseLemmingOnBoardCounter() { g.increaseLemmingOnBoardCounter(); }
	
	
	/*---CHECKERS---*/
	
	public boolean isSolid() { return true; }
	
	public boolean isAlive() { return this.alive; }
	
	public boolean isInAir(Position p) { return this.g.isInAir(p); }
	
	public boolean isInPosition(Position p) { return this.pos.equals(p); }

	
	/*---INTERACTIONS---*/
	
	public void interactWith(Lemming l) {}

	public void interactWith(Wall w) {}

	public void interactWith(ExitDoor ed) {}
	
	public void interactWith(MetalWall mW) {}
	
	public void parameterObjInteractWithOthers(GameItem obj) { g.parameterObjInteractWithOthers(obj);}
	
	public void resetInteraction() {}	
	
	
	/*---OTHERS---*/
	
	public void lemmingArrived() { this.g.lemmingArrived(); }
	
	public void lemmingDead() { this.g.lemmingDead(); }
	
	
	/*El gameUpdate() ya no es necesario. El double-dispatch nos permite delegar la propia función de
	 actualizar el objeto en cuestión en función de la interacción que haga con otro objeto*/
}
