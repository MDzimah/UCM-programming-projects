package tp1.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import tp1.exceptions.OffBoardException;
import java.io.IOException;
import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.logic.gameobjects.*;
import tp1.logic.lemmingsRole.*;
import tp1.view.Messages;

//Game presents itself in these three "ways" to other classes
public class Game implements GameModel, GameStatus, GameWorld {
	public static final int DIM_X = 10;
	public static final int DIM_Y = 10;
	
	private GameObjectContainer goc;	
	private GameConfiguration gc;
	private int cycle;	
	private int numLemmingsInBoard;
	private int numDeadLems;
	private int numLemmingsExit;
	private int numLemmingsToWin;
	private int nLevel;
	private boolean exitsGame;
	
	public Game(int nLevel) {
		this.goc = new GameObjectContainer();
		this.gc = new LevelGameConfiguration(nLevel, this);
		this.nLevel = nLevel;
		this.setGameConfiguration();
		this.exitsGame = false;
	}
	
	
	/*---GAME STATUS---*/
	
	@Override
	public int getCycle() { return this.cycle; }
	
	@Override
	public int numLemmingsInBoard() { return this.numLemmingsInBoard; }
	
	@Override
	public int numLemmingsDead() { return this.numDeadLems; }

	@Override
	public int numLemmingsExit() { return this.numLemmingsExit; }

	@Override
	public int numLemmingsToWin() { return this.numLemmingsToWin; }	
	
	@Override
	public int getLevel(){ return this.nLevel; }
	
	@Override	
	public boolean playerWins() { return this.numLemmingsInBoard == 0 && this.numLemmingsExit >= this.numLemmingsToWin; }

	@Override
	public boolean playerLooses() { return this.numLemmingsInBoard == 0 && this.numLemmingsExit < this.numLemmingsToWin; }	
	
	@Override
	public String positionToString(int c, int f) { return this.goc.positionToString(new Position(f,c)); }
		
	
	/*---GAME MODEL---*/
	
	@Override
	public void load (String fileName) throws GameLoadException {
		this.gc = new FileGameConfiguration(fileName, this);
		this.setGameConfiguration();
		
		/*To take advantage of GameConfiguration's polymorphism, nLevel has to be set to -1 so as to not
		initialize it in the reset method to its wrong implemented class*/
		this.nLevel = -1;
	}
	
	@Override
	public void save (String fileName) throws GameModelException {
		File f = new File("configurations/" + fileName + ".txt");
		
		try(BufferedWriter bf = new BufferedWriter(new FileWriter(f))){
			bf.write(this.toString());
		}
		catch (IOException e) {
			throw new GameModelException(Messages.FILE_NOT_SAVED.formatted(fileName));
		}
	}
	
	@Override
	public void exit() { this.exitsGame = true; }
	
	@Override
	public boolean isFinished() { return this.exitsGame || this.playerLooses() || this.playerWins(); }
	
	@Override
	public void reset(int nLevel) {
		//Look at load method for explanation
		if (nLevel >= 0)
			this.gc = new LevelGameConfiguration(nLevel, this);
		
		this.setGameConfiguration();
	}
	
	@Override
	public boolean setRole(LemmingRole r, Position p) throws OffBoardException { return this.goc.setRole(r, p); }
	
	@Override
	public void update() {
		this.goc.update();
		++this.cycle;
	}

	
	/*---GAME WORLD---*/
	
	@Override
	public void lemmingArrived() {
		++this.numLemmingsExit;
		--this.numLemmingsInBoard;
	}
	
	@Override
	public void lemmingDead() {
		++this.numDeadLems;
		--this.numLemmingsInBoard;
	}
	
	@Override
	public boolean isInAir(Position pos) { return !this.goc.isSolid(pos); }
	
	@Override
	public void parameterObjInteractWithOthers(GameItem obj) {
		this.goc.parameterObjInteractWithOthers(obj);
	}
	
	@Override
	public void add(GameObject gObj) { this.goc.add(gObj); }
	
	
	/*---OTHERS---*/
	
	//Used for the SaveCommand
	@Override
	public String toString() {
		return this.cycle + " " + this.numLemmingsInBoard + " " + this.numDeadLems + " "
				+ this.numLemmingsExit + " " + this.numLemmingsToWin 
				+ Messages.LINE_SEPARATOR 
				+ this.goc.toString();
	}
	
	/*---PRIVATE---*/
	
	private void setGameConfiguration() {
		this.cycle = this.gc.getCycle();
		this.numLemmingsInBoard = this.gc.numLemmingsInBoard();
		this.numDeadLems = this.gc.numLemmingsDead();
		this.numLemmingsExit = this.gc.numLemmingsExit();
		this.numLemmingsToWin = this.gc.numLemmingsToWin();
		
		this.goc = gc.getGameObjects(this);
	}
}