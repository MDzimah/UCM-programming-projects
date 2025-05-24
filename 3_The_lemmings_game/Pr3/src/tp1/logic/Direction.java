package tp1.logic;

import tp1.view.Messages;

//Represents the allowed directions in the game
public enum Direction {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), 
	LEFT_DOWN(-1,1), LEFT_UP(-1,-1), RIGHT_DOWN(1,1), RIGHT_UP(1,-1), 
	NONE(0,0), NOT_VALID(0,0);
	
	private final int x;
	private final int y;
	
	private Direction(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	/*---GETTERS---*/
	
	int getX() { return this.x; }	
	int getY() { return this.y; }
	
	public static Direction getNewDir(Direction d) {
		switch(d) {
		case LEFT: return LEFT;
		case RIGHT: return RIGHT;
		case DOWN: return DOWN;
		case UP: return UP;
		case LEFT_DOWN: return LEFT_DOWN;
		case LEFT_UP: return LEFT_UP;
		case RIGHT_DOWN: return RIGHT_DOWN;
		case RIGHT_UP: return RIGHT_UP;
		case NONE: return NONE;
		default: return NOT_VALID;
		}
	}
	
	public static Direction getNewDir(String dir) {
		switch(dir.toLowerCase()) {
		case Messages.LEFT: return LEFT;
		case Messages.RIGHT: return RIGHT;
		case Messages.DOWN: return DOWN;
		case Messages.UP: return UP;
		case Messages.LEFT_DOWN: return LEFT_DOWN;
		case Messages.LEFT_UP: return LEFT_UP;
		case Messages.RIGHT_DOWN: return RIGHT_DOWN;
		case Messages.RIGHT_UP: return RIGHT_UP;
		case Messages.NONE: return NONE;
		default: return NOT_VALID;
		}
	}

}
