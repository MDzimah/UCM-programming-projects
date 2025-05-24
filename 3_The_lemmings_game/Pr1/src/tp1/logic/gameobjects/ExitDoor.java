package tp1.logic.gameobjects;

import tp1.logic.*;

public class ExitDoor {
	private Position pos;
	
	public ExitDoor(Position p) {
		this.pos = p;
	}
	
	public boolean isInPosition(Position p) {
		return p.equals(this.pos);
	}
}
