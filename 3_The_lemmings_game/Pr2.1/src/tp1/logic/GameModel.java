package tp1.logic;

public interface GameModel {
	public abstract void update();
	public abstract void exit();
	public abstract boolean isFinished();
	public abstract void reset();
}
