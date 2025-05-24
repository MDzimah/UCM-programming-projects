package tp1.logic;
import tp1.logic.gameobjects.*;

public class Game {

	public static final int DIM_X = 10;
	public static final int DIM_Y = 10;
	
	private GameObjectContainer goc;
	private int numDeadLems;
	private int numLemmingsExit;
	private int numLemmingsToWin;
	private int numLemmingsInBoard;
	private int cycle;
	private int nLevel;
	
	public Game(int nLevel) {
		this.cycle = 0;
		this.goc = new GameObjectContainer();
		this.nLevel = nLevel;
		this.numLemmingsExit = this.numLemmingsInBoard = this.numDeadLems = 0;
	}
	
	public void add(Lemming l) {
		this.goc.add(l);
		++this.numLemmingsInBoard;
	}
	
	public void add(ExitDoor eD) {
		this.goc.add(eD);
	}
	
	public void add(Wall w) {
		this.goc.add(w);
	}
	
	public int getLevel(){
		return this.nLevel;
	}
	
	public int getCycle() {
		return this.cycle;
	}
	
	
	public int numLemmingsInBoard() {
		return this.goc.numLemsInBoard();
	}

	public int numLemmingsDead() {
		return this.numDeadLems;
	}

	public int numLemmingsExit() {
		return this.numLemmingsExit;
	}

	public int numLemmingsToWin() {
		return this.numLemmingsToWin;
	}

	
	public boolean playerWins() {
		return this.goc.numLemsInBoard() == 0 && this.numLemmingsExit >= this.numLemmingsToWin;
	}

	public boolean playerLooses() {
		return this.goc.numLemsInBoard() == 0 && this.numLemmingsExit < this.numLemmingsToWin;
	}
	
	public boolean isFinished() {
		return this.playerLooses() || this.playerWins() ;
	}
	
	
	public boolean isInAir(Position pos) {
		return !this.goc.isSolid(pos);
	}
	
	public boolean isSolid(Position pos) {
		return this.goc.isSolid(pos);
	}

	
	public boolean isExit(Position pos) {
		return this.goc.isExit(pos);
	}

	
	private void lemmingExit() {
		this.goc.removeLemsExit();
		
		/*numLemsInBoard() (tamaño de arrayList de Lemmings) se actualiza en la instrucción de antes. 
		La diferencia entre el valor del atributo numLemmingsInBoard de Game y el anterior resulta en los lemmings que han salido 
		en el ciclo anterior*/
		
		this.numLemmingsExit += (this.numLemmingsInBoard - this.goc.numLemsInBoard());
		this.numLemmingsInBoard = this.goc.numLemsInBoard();
	}
	
	private void lemmingDead() {
		this.goc.removeDeadLems();
		
		//Análogo al método lemmingExit()
		this.numDeadLems += (this.numLemmingsInBoard - this.goc.numLemsInBoard());
		this.numLemmingsInBoard = this.goc.numLemsInBoard();
	}

	
	public String positionToString(int c, int f) {
		return this.goc.positionToString(new Position(f,c));
	}
	
	
	public void update() {
		this.goc.update();
		this.lemmingExit();		
		this.lemmingDead();
		++this.cycle;
	}



public void level0or1(){
	this.add(new Lemming(this, new Position(0,9), new WalkerRole()));
	this.add(new Lemming(this, new Position(3,2), new WalkerRole()));
	this.add(new Lemming(this, new Position(8,0), new WalkerRole()));
	if (this.nLevel == 1) this.add(new Lemming(this, new Position(3,3), new WalkerRole()));
	
	this.add(new Wall(new Position(1,8)));
	this.add(new Wall(new Position(1,9)));
	
	this.add(new Wall(new Position(4,2)));
	this.add(new Wall(new Position(4,3)));
	this.add(new Wall(new Position(4,4)));
	
	this.add(new Wall(new Position(5,7)));
	
	this.add(new Wall(new Position(6,4)));
	this.add(new Wall(new Position(6,5)));
	this.add(new Wall(new Position(6,6)));
	this.add(new Wall(new Position(6,7)));
	
	this.add(new Wall(new Position(8,8)));
	
	this.add(new Wall(new Position(9,0)));
	this.add(new Wall(new Position(9,1)));
	this.add(new Wall(new Position(9,8)));
	this.add(new Wall(new Position(9,9)));
	
	this.add(new ExitDoor(new Position(5,4)));
	
	this.numLemmingsToWin = 2;
}

public void level2() {
	this.add(new Lemming(this, new Position(9,9), new WalkerRole()));
	this.add(new Lemming(this, new Position(9,2), new WalkerRole()));
	this.add(new Lemming(this, new Position(9,3), new WalkerRole()));
	this.add(new Lemming(this, new Position(0,0), new WalkerRole()));
	this.add(new Lemming(this, new Position(0,2), new WalkerRole()));
	this.add(new Lemming(this, new Position(0,2), new WalkerRole()));
	this.add(new Lemming(this, new Position(0,6), new WalkerRole()));
	this.add(new Lemming(this, new Position(1,5), new WalkerRole()));
	this.add(new Lemming(this, new Position(0,9), new WalkerRole()));
	this.add(new Lemming(this, new Position(0,4), new WalkerRole()));
	this.add(new Lemming(this, new Position(7,6), new WalkerRole()));
	this.add(new Lemming(this, new Position(4,2), new WalkerRole()));
	this.add(new Lemming(this, new Position(3,9), new WalkerRole()));

	this.add(new Wall(new Position(2,0)));
	this.add(new Wall(new Position(4,1)));
	this.add(new Wall(new Position(3,2)));
	this.add(new Wall(new Position(6,0)));
	this.add(new Wall(new Position(1,2)));
	this.add(new Wall(new Position(2,3)));
	this.add(new Wall(new Position(4,4)));
	this.add(new Wall(new Position(2,5)));

	
	this.add(new Wall(new Position(1,6)));
	this.add(new Wall(new Position(1,7)));
	this.add(new Wall(new Position(1,8)));
	
	
	this.add(new Wall(new Position(5,8)));
	this.add(new Wall(new Position(6,7)));
	
	this.add(new Wall(new Position(6,5)));
	this.add(new Wall(new Position(8,5)));
	this.add(new Wall(new Position(5,2)));
	this.add(new Wall(new Position(8,4)));
	this.add(new Wall(new Position(7,3)));
	this.add(new Wall(new Position(7,2)));
	this.add(new Wall(new Position(7,1)));
	
	
	this.add(new Wall(new Position(8,6)));
	this.add(new Wall(new Position(8,7)));
	this.add(new Wall(new Position(8,8)));
	this.add(new Wall(new Position(8,9)));
	
	this.add(new ExitDoor(new Position(7,6)));
	this.add(new ExitDoor(new Position(3,9)));
	this.add(new ExitDoor(new Position(0,9)));

	
	this.numLemmingsToWin = 6;
}
}