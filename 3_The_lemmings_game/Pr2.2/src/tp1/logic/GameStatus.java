package tp1.logic;

public interface GameStatus {
	
	/*---GETTERS---*/
    public abstract int getCycle();
    public abstract int getLevel();
    public abstract int numLemmingsInBoard();    
    public abstract int numLemmingsDead();
	public abstract int numLemmingsExit();
	public abstract int numLemmingsToWin(); 
	
	/*---CHECKERS---*/
	public abstract boolean playerWins();
    public abstract boolean playerLooses();
    
    /*---OTHERS---*/
    public abstract String positionToString(int c, int f);
	
}
