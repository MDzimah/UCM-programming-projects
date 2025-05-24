package tp1.logic;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;
import tp1.logic.lemmingsRole.ParachuterRole;
import tp1.logic.lemmingsRole.WalkerRole;

//When reset command is executed with a level number as a parameter, this class retrieves the pre-made level 
public class LevelGameConfiguration implements GameConfiguration {
	public static final int numberOfLevels = 4;
	
	private GameObjectContainer goc;
	private int cycle;	
	private int numLemmingsInBoard;
	private int numDeadLems;
	private int numLemmingsExit;
	private int numLemmingsToWin;
	private int nLevel;
	
	LevelGameConfiguration(int nLevel, GameWorld g) {
		this.nLevel = nLevel;
		this.goc = new GameObjectContainer();
		
		switch(this.nLevel) {
		case 0: 
		case 1: 
		case 2: 
			this.level0or1or2(g);
			break;
		case 3: 
			this.level3(g);
			break;
		}
	}

	
	/*---GETTERS---*/
	
	@Override
	public int getCycle() { return this.cycle; }

	@Override
	public int numLemmingsInBoard() { return this.numLemmingsInBoard; }

	@Override
	public int numLemmingsDead() { return this.numDeadLems; }

	@Override
	public int numLemmingsExit() { return this.numLemmingsExit; }

	@Override
	public int numLemmingsToWin() {	return this.numLemmingsToWin; }

	public int getLevel() { return this.nLevel; }
	
	@Override
	public GameObjectContainer getGameObjects(GameWorld g) { return new GameObjectContainer(this.goc, g); }

	
	/*---PRIVATE---*/
	
	private void level0or1or2(GameWorld g){	
		
		this.numLemmingsInBoard = 3;
		
		this.goc.add(new Lemming(new Position(0,9), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(3,2), new WalkerRole(), g));
		this.goc.add(new Lemming( new Position(8,0), new WalkerRole(), g));

		
		this.goc.add(new Wall(new Position(1,8), g));
		this.goc.add(new Wall(new Position(1,9), g));
		this.goc.add(new Wall(new Position(4,2), g));
		this.goc.add(new Wall(new Position(4,3), g));
		this.goc.add(new Wall(new Position(4,4), g));
		
		this.goc.add(new Wall(new Position(5,7), g));
		this.goc.add(new Wall(new Position(6,4), g));
		this.goc.add(new Wall(new Position(6,5), g));
		this.goc.add(new Wall(new Position(6,6), g));
		this.goc.add(new Wall(new Position(6,7), g));
		
		this.goc.add(new Wall(new Position(8,8), g));
		
		this.goc.add(new Wall(new Position(9,0), g));
		this.goc.add(new Wall(new Position(9,1), g));
		this.goc.add(new Wall(new Position(9,8), g));
		this.goc.add(new Wall(new Position(9,9), g));
		
		this.goc.add(new ExitDoor(new Position(5,4), g));
		
		/*No specification is given for the entrance of lemmings into an exit, 
		therefore, the level is adjusted to the corresponding test results*/
		if (this.nLevel == 1 || this.nLevel == 2) {
			this.goc.add(new Lemming(new Position(3,3), new WalkerRole(), g));	
			++this.numLemmingsInBoard;
		}
		if (this.nLevel == 2) {
			this.goc.add(new Lemming(new Position(0,6), new WalkerRole(), g));
			this.goc.add(new Lemming(new Position(0,6), new ParachuterRole(), g));
			this.numLemmingsInBoard += 2;
			this.goc.add(new Wall(new Position(5, 3), g));
			this.goc.add(new MetalWall(new Position(6,3), g));
		}
		this.cycle = this.numLemmingsExit = this.numDeadLems = 0;
		this.numLemmingsToWin = 2;
	}
	
	private void level3(GameWorld g) {	
		
		this.numLemmingsInBoard = 0;
		
		this.goc.add(new Lemming(new Position(0,0), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(9,9), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(9,2), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(9,3), new WalkerRole(), g));
		
		this.goc.add(new Lemming(new Position(0,2), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(0,2), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(0,6), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(1,5), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(0,9), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(0,4), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(7,6), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(4,2), new WalkerRole(), g));
		this.goc.add(new Lemming(new Position(3,9), new WalkerRole(), g));
	
		
		this.goc.add(new Wall(new Position(4,1), g));
		this.goc.add(new Wall(new Position(3,2), g));
		this.goc.add(new Wall(new Position(6,0), g));
		this.goc.add(new Wall(new Position(1,2), g));
		this.goc.add(new Wall(new Position(2,3), g));
		this.goc.add(new Wall(new Position(4,4), g));
		this.goc.add(new Wall(new Position(2,5), g));
		this.goc.add(new Wall(new Position(2,0), g));
	
		
		this.goc.add(new Wall(new Position(1,6), g));
		this.goc.add(new Wall(new Position(1,7), g));
		this.goc.add(new Wall(new Position(1,8), g));
		
		
		this.goc.add(new Wall(new Position(5,8), g));
		this.goc.add(new Wall(new Position(6,7), g));
		
		this.goc.add(new Wall(new Position(6,5), g));
		this.goc.add(new Wall(new Position(8,5), g));
		this.goc.add(new Wall(new Position(5,2), g));
		this.goc.add(new Wall(new Position(8,4), g));
		this.goc.add(new Wall(new Position(7,3), g));
		this.goc.add(new Wall(new Position(7,2), g));
		this.goc.add(new Wall(new Position(7,1), g));
		
		
		this.goc.add(new Wall(new Position(8,6), g));
		this.goc.add(new Wall(new Position(8,7), g));
		this.goc.add(new Wall(new Position(8,8), g));
		this.goc.add(new Wall(new Position(8,9), g));
		
		this.goc.add(new ExitDoor(new Position(7,6), g));
		this.goc.add(new ExitDoor(new Position(3,9), g));	
		this.goc.add(new ExitDoor(new Position(7,9), g));
		
		this.cycle = this.numLemmingsExit = this.numDeadLems = 0;
		this.numLemmingsToWin = 6;
	}
}
