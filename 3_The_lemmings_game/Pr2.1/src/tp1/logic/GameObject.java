package tp1.logic;

public abstract class GameObject {
	private Position pos;
	private GameWorld g;
	private Boolean alive;
	
	public GameObject (Position p, GameWorld g)
	{
		this.pos = p;
		this.g = g;
		this.alive = true;
	}
	
	public boolean isInPosition(Position p) {
		return this.pos.equals(p);
	}
	
	public boolean isAlive() { return this.alive; }
	
	protected void dead() { this.alive = false; }

	protected Position createPosition(Direction d) { Position p = new Position(this.pos, d); return p; }

	protected void changePosition(Position p) { this.pos = p; }
	
	protected boolean isInAir(Position p) { return this.g.isInAir(p); }
	
	protected boolean isOnExit (Position p) { return this.g.lemmingOnExit(p); }
	
	protected void lemmingArrived() { this.g.lemmingArrived(); }
	
	protected void lemmingDead() { this.g.lemmingDead(); }
	
	protected abstract boolean isExit(Position p);
	
	protected abstract boolean isSolid(Position p);
	
	public abstract void update();
	
	protected abstract String getIcon();
}
