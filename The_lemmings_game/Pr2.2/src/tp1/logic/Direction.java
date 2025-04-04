package tp1.logic;

/**
 * Represents the allowed directions in the game
 *
 */
public enum Direction {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), NONE(0,0);
	
	private int x;
	private int y;
	
	private Direction(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	/*---GETTERS---*/
	
	protected int getX() { return this.x; }	
	
	protected int getY() { return this.y; }	
}
