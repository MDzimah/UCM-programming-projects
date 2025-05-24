package tp1.logic;

public interface GameStatus {
    public abstract int getCycle();
    public abstract int getLevel();
    public abstract int numLemmingsInBoard();    
    public abstract int numLemmingsDead();
	public abstract int numLemmingsExit();
	public abstract int numLemmingsToWin(); 
	
    public abstract String positionToString(int c, int f);
	
	public abstract boolean playerWins();
    public abstract boolean playerLooses();
}
