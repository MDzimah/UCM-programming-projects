package tp1.logic;

public interface GameStatus extends GameCounters {
	/*---CHECKERS---*/
	
	public abstract boolean playerWins();
    public abstract boolean playerLooses();
    
    
    /*---OTHERS---*/
    
    public abstract String positionToString(int c, int f);
}
