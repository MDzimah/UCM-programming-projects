package tp1.logic;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.OffBoardException;
import tp1.logic.lemmingsRole.LemmingRole;

public interface GameModel {
	
	/*---GETTERS---*/
	
	public abstract int getLevel();
	
	
	/*---SETTERS---*/
	
	public abstract void load(String fileName) throws GameLoadException;
	public abstract void save(String fileName) throws GameModelException;
	
	public abstract void exit();
	
	
	/*---CHECKERS---*/
	
	public abstract boolean isFinished();
	
	
	/*---OTHERS---*/
	
	public abstract void reset(int nLevel);
	
	public abstract boolean setRole(LemmingRole r, Position p) throws OffBoardException;
	
	public abstract void update();
}
