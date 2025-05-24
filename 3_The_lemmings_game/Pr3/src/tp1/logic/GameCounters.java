package tp1.logic;

public interface GameCounters {
	public abstract int getCycle();
	public abstract int numLemmingsInBoard();
	public abstract int numLemmingsDead();
	public abstract int numLemmingsExit();
	public abstract int numLemmingsToWin();
}
