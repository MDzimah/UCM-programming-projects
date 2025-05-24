package tp1.logic;
import tp1.logic.gameobjects.*;
import tp1.logic.lemmingsRole.*;

public class Game implements GameModel, GameStatus, GameWorld{
	public static final int DIM_X = 10;
	public static final int DIM_Y = 10;
	
	private GameObjectContainer goc;
	private int numDeadLems;
	private int numLemmingsExit;
	private int numLemmingsToWin;
	private int numLemmingsInBoard;
	private int cycle;
	private int nLevel;
	private int numberOfLevels;
	private boolean exitsGame;
	
	public Game(int nLevel, int numLevels) {
		this.nLevel = nLevel;
		this.numberOfLevels = numLevels;
		this.reset(nLevel);
		this.exitsGame = false;
	}
	
	
	/*---GAME STATUS---*/
	
	@Override
	public String positionToString(int c, int f) { return this.goc.positionToString(new Position(f,c)); }
	
	@Override
	public int getCycle() { return this.cycle; }
	
	@Override
	public int numLemmingsInBoard() { return this.numLemmingsInBoard; }
	
	@Override
	public int getLevel(){ return this.nLevel; }

	@Override
	public int numLemmingsDead() { return this.numDeadLems; }

	@Override
	public int numLemmingsExit() { return this.numLemmingsExit; }

	@Override
	public int numLemmingsToWin() { return this.numLemmingsToWin; }	

	@Override	
	public boolean playerWins() { return this.numLemmingsInBoard == 0 && this.numLemmingsExit >= this.numLemmingsToWin; }

	@Override
	public boolean playerLooses() { return this.numLemmingsInBoard == 0 && this.numLemmingsExit < this.numLemmingsToWin; }	

		
	/*---GAME MODEL---*/
	
	@Override
	public int getNumLevels() { return this.numberOfLevels; }
	
	@Override
	public void exit() { this.exitsGame = true; }
	
	@Override
	public boolean isFinished() { return this.exitsGame || this.playerLooses() || this.playerWins(); }
	
	@Override
	public void reset(int nLevel) {
		this.goc = new GameObjectContainer();
		this.cycle = this.numLemmingsExit = this.numLemmingsInBoard = this.numDeadLems = 0;
		this.nLevel = nLevel;
		
		switch(this.nLevel) {
		case 0: 
		case 1: 
		case 2: 
			this.level0or1or2();
			break;
		case 3: 
			this.level3();
			break;
		}
	}
	
	@Override
	public boolean setRole(LemmingRole r, Position p) { return this.goc.setRole(r, p); }
	
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
	public void increaseLemmingOnBoardCounter() { ++this.numLemmingsInBoard; }
	
	@Override
	public boolean isInAir(Position pos) { return !this.goc.isSolid(pos); }
	
	@Override
	public void parameterObjInteractWithOthers(GameItem obj) {
		this.goc.parameterObjInteractWithOthers(obj);
	}
	
	@Override
	public void add(GameObject go) { this.goc.add(go); }
				
		
	/*---PRIVATE---*/
		
	private void level0or1or2(){		
		this.add(new Lemming(new Position(0,9), new WalkerRole(), this));
		this.add(new Lemming(new Position(3,2), new WalkerRole(), this));
		this.add(new Lemming( new Position(8,0), new WalkerRole(), this));

		
		this.add(new Wall(new Position(1,8), this));
		this.add(new Wall(new Position(1,9), this));
		
		this.add(new Wall(new Position(4,2), this));
		this.add(new Wall(new Position(4,3), this));
		this.add(new Wall(new Position(4,4), this));
		
		this.add(new Wall(new Position(5,7), this));
		
		this.add(new Wall(new Position(6,4), this));
		this.add(new Wall(new Position(6,5), this));
		this.add(new Wall(new Position(6,6), this));
		this.add(new Wall(new Position(6,7), this));
		
		this.add(new Wall(new Position(8,8), this));
		
		this.add(new Wall(new Position(9,0), this));
		this.add(new Wall(new Position(9,1), this));
		this.add(new Wall(new Position(9,8), this));
		this.add(new Wall(new Position(9,9), this));
		
		this.add(new ExitDoor(new Position(5,4), this));
		
		/*Los "expected" están ajustados a una configuración determinada del GameObjectContainer, por eso se ha
		hecho este cambio en el orden de añadir los objetos al GameObjectContainer*/
		if (this.nLevel == 1 || this.nLevel == 2) this.add(new Lemming(new Position(3,3), new WalkerRole(), this));		
		if (this.nLevel == 2) {
			this.add(new Lemming(new Position(0,6), new WalkerRole(), this));
			this.add(new Lemming(new Position(0,6), new ParachuterRole(), this));
			this.add(new Wall(new Position(5, 3), this));
			this.add(new MetalWall(new Position(6,3), this));
		}

		this.numLemmingsToWin = 2;
	}
	
	private void level3() {	
		this.add(new Lemming(new Position(0,0), new WalkerRole(), this));
		this.add(new Lemming(new Position(9,9), new WalkerRole(), this));
		this.add(new Lemming(new Position(9,2), new WalkerRole(), this));
		this.add(new Lemming(new Position(9,3), new WalkerRole(), this));
		
		this.add(new Lemming(new Position(0,2), new WalkerRole(), this));
		this.add(new Lemming(new Position(0,2), new WalkerRole(), this));
		this.add(new Lemming(new Position(0,6), new WalkerRole(), this));
		this.add(new Lemming(new Position(1,5), new WalkerRole(), this));
		this.add(new Lemming(new Position(0,9), new WalkerRole(), this));
		this.add(new Lemming(new Position(0,4), new WalkerRole(), this));
		this.add(new Lemming(new Position(7,6), new WalkerRole(), this));
		this.add(new Lemming(new Position(4,2), new WalkerRole(), this));
		this.add(new Lemming(new Position(3,9), new WalkerRole(), this));
	
		
		this.add(new Wall(new Position(4,1), this));
		this.add(new Wall(new Position(3,2), this));
		this.add(new Wall(new Position(6,0), this));
		this.add(new Wall(new Position(1,2), this));
		this.add(new Wall(new Position(2,3), this));
		this.add(new Wall(new Position(4,4), this));
		this.add(new Wall(new Position(2,5), this));
		this.add(new Wall(new Position(2,0), this));
	
		
		this.add(new Wall(new Position(1,6), this));
		this.add(new Wall(new Position(1,7), this));
		this.add(new Wall(new Position(1,8), this));
		
		
		this.add(new Wall(new Position(5,8), this));
		this.add(new Wall(new Position(6,7), this));
		
		this.add(new Wall(new Position(6,5), this));
		this.add(new Wall(new Position(8,5), this));
		this.add(new Wall(new Position(5,2), this));
		this.add(new Wall(new Position(8,4), this));
		this.add(new Wall(new Position(7,3), this));
		this.add(new Wall(new Position(7,2), this));
		this.add(new Wall(new Position(7,1), this));
		
		
		this.add(new Wall(new Position(8,6), this));
		this.add(new Wall(new Position(8,7), this));
		this.add(new Wall(new Position(8,8), this));
		this.add(new Wall(new Position(8,9), this));
		
		this.add(new ExitDoor(new Position(7,6), this));
		this.add(new ExitDoor(new Position(3,9), this));	
		this.add(new ExitDoor(new Position(7,9), this));
		
		this.numLemmingsToWin = 6;
	}
}