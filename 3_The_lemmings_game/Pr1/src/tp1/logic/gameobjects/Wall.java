package tp1.logic.gameobjects;

import tp1.logic.*;

public class Wall {
	private Position pos;
	
	public Wall(Position p) {
		this.pos = p;
	}
	
	public boolean isInPosition(Position p) {
		return p.equals(this.pos);
	}
}
