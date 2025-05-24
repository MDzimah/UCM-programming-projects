package tp1.logic;

import tp1.logic.lemmingsRole.LemmingRole;

public interface GameModel {
	
	/*---GETTERS---*/
	public abstract int getNumLevels();
	public abstract int getLevel();
	
	/*---SETTERS---*/
	public abstract void exit();
	
	/*---CHECKERS---*/
	public abstract boolean isFinished();
	
	/*---OTHERS---*/
	public abstract void reset(int nLevel);
	public abstract boolean setRole(LemmingRole r, Position p);
	public abstract void update();
	
}
