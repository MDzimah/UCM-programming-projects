package tp1.logic;

/**
 * 
 * Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {
	private final int col;
	private final int fila;
	
	public Position(int f, int c) {
		this.fila = f;
		this.col = c;
	}
	
	public Position(Position p, Direction d) {
		this.fila = p.fila + d.getY();;
		this.col = p.col + d.getX();;
	}
	
	public boolean equals(Position p) {
		return this.fila == p.fila && this.col == p.col;
	}
	
	public boolean isInBoard()
	{
		return this.col >= 0 && this.col < Game.DIM_X 
				&& this.fila >= 0 && this.fila < Game.DIM_Y;
	}
}
