package tp1.logic;

import tp1.logic.gameobjects.GameItem;

public interface GameWorld {
	/*---SETTERS---*/
	
	public abstract void lemmingArrived();
	public abstract void lemmingDead();
	
	
	/*---CHECKERS---*/
	
	public abstract boolean isInAir(Position pos);
	
	
	/*---OTHERS---*/
	
	public abstract void parameterObjInteractWithOthers(GameItem obj);
	
	public abstract void add(GameObject gObj);
}
