package tp1.logic;
import tp1.logic.gameobjects.*;

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
	private boolean exitsGame;
	
	public Game(int nLevel) {
		this.nLevel = nLevel;
		this.reset();
		this.exitsGame = false;
	}
	
	
	//GameStatus métodos
		@Override
		public String positionToString(int c, int f) {
			return this.goc.positionToString(new Position(f,c));
		}
		
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
	
		
	//GameModel métodos
		public boolean isFinished() {
			return this.exitsGame || this.playerLooses() || this.playerWins();
		}
		
		public void reset() {
			this.goc = new GameObjectContainer();
			this.cycle = this.numLemmingsExit = this.numLemmingsInBoard = this.numDeadLems = 0;
			
			switch(this.getLevel()) {
			case 0: 
			case 1: 
				this.level0or1();
				break;
			case 2: this.level2();
				break;
			}
			
			this.numLemmingsInBoard = this.goc.numLemsStart();
		}
		
		public void exit() { this.exitsGame = true; }
		
		public void update() {
			this.goc.update();
			++this.cycle;
		}

		
	//Métodos de GameWorld
		public void add(GameObject go) {
			this.goc.add(go);
		}
		
		public boolean isInAir(Position pos) {
			return !this.goc.isSolid(pos);
		}
		
		public boolean lemmingOnExit(Position pos) { 
			return this.goc.isExit(pos); 
		}
		
		public void lemmingArrived() {
			++this.numLemmingsExit;
			--this.numLemmingsInBoard;
		}
		
		public void lemmingDead() {
			++this.numDeadLems;
			--this.numLemmingsInBoard;
		}	
		
	
		
	//Otros Métodos	
		private void level0or1(){
			this.add(new Lemming(this, new Position(0,9)));
			this.add(new Lemming(this, new Position(3,2)));
			this.add(new Lemming(this, new Position(8,0)));
			if (this.nLevel == 1) this.add(new Lemming(this, new Position(3,3)));
			
			this.add(new Wall(this, new Position(1,8)));
			this.add(new Wall(this, new Position(1,9)));
			
			this.add(new Wall(this, new Position(4,2)));
			this.add(new Wall(this, new Position(4,3)));
			this.add(new Wall(this, new Position(4,4)));
			
			this.add(new Wall(this, new Position(5,7)));
			
			this.add(new Wall(this, new Position(6,4)));
			this.add(new Wall(this, new Position(6,5)));
			this.add(new Wall(this, new Position(6,6)));
			this.add(new Wall(this, new Position(6,7)));
			
			this.add(new Wall(this, new Position(8,8)));
			
			this.add(new Wall(this, new Position(9,0)));
			this.add(new Wall(this, new Position(9,1)));
			this.add(new Wall(this, new Position(9,8)));
			this.add(new Wall(this, new Position(9,9)));
			
			this.add(new ExitDoor(this, new Position(5,4)));
			
			this.numLemmingsToWin = 2;
		}
		
		private void level2() {
			this.add(new Lemming(this, new Position(9,9)));
			this.add(new Lemming(this, new Position(9,2)));
			this.add(new Lemming(this, new Position(9,3)));
			this.add(new Lemming(this, new Position(0,0)));
			this.add(new Lemming(this, new Position(0,2)));
			this.add(new Lemming(this, new Position(0,2)));
			this.add(new Lemming(this, new Position(0,6)));
			this.add(new Lemming(this, new Position(1,5)));
			this.add(new Lemming(this, new Position(0,9)));
			this.add(new Lemming(this, new Position(0,4)));
			this.add(new Lemming(this, new Position(7,6)));
			this.add(new Lemming(this, new Position(4,2)));
			this.add(new Lemming(this, new Position(3,9)));
		
			this.add(new Wall(this, new Position(2,0)));
			this.add(new Wall(this, new Position(4,1)));
			this.add(new Wall(this, new Position(3,2)));
			this.add(new Wall(this, new Position(6,0)));
			this.add(new Wall(this, new Position(1,2)));
			this.add(new Wall(this, new Position(2,3)));
			this.add(new Wall(this, new Position(4,4)));
			this.add(new Wall(this, new Position(2,5)));
		
			
			this.add(new Wall(this, new Position(1,6)));
			this.add(new Wall(this, new Position(1,7)));
			this.add(new Wall(this, new Position(1,8)));
			
			
			this.add(new Wall(this, new Position(5,8)));
			this.add(new Wall(this, new Position(6,7)));
			
			this.add(new Wall(this, new Position(6,5)));
			this.add(new Wall(this, new Position(8,5)));
			this.add(new Wall(this, new Position(5,2)));
			this.add(new Wall(this, new Position(8,4)));
			this.add(new Wall(this, new Position(7,3)));
			this.add(new Wall(this, new Position(7,2)));
			this.add(new Wall(this, new Position(7,1)));
			
			
			this.add(new Wall(this, new Position(8,6)));
			this.add(new Wall(this, new Position(8,7)));
			this.add(new Wall(this, new Position(8,8)));
			this.add(new Wall(this, new Position(8,9)));
			
			this.add(new ExitDoor(this, new Position(7,6)));
			this.add(new ExitDoor(this, new Position(3,9)));	
			this.add(new ExitDoor(this, new Position(7,9)));	
			
			this.numLemmingsToWin = 6;
		}
}