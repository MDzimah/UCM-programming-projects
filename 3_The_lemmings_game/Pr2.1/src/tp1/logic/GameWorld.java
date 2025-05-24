package tp1.logic;

public interface GameWorld {
	public abstract boolean isInAir(Position pos);
	public abstract boolean lemmingOnExit(Position pos);
	public abstract void lemmingArrived();
	public abstract void lemmingDead();
	public abstract void add(GameObject go);
}
