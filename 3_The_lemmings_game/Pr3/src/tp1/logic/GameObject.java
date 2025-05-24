package tp1.logic;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;
import tp1.logic.lemmingsRole.LemmingRole;
import tp1.view.Messages;

public abstract class GameObject implements GameItem {
	private Position pos;
	private GameWorld g;
	private boolean alive;
	
	public GameObject () { }
	
	public GameObject (Position p, GameWorld g) {
		this.pos = p;
		this.g = g;
		this.alive = true;
	}
	
	/*---GETTERS---*/
	
	public Position createPosition(Direction d) { Position p = new Position(this.pos, d); return p; }	
	
	protected abstract GameObject createNewObject (Position p, GameWorld g);
	
	/*---SETTERS---*/
	
	public boolean setRole(LemmingRole r) { return false; }
	
	public void dead() { this.alive = false; }
	
	protected abstract String getIcon();
	
	protected void changePosition(Position p) { this.pos = p; }
	
	
	/*---CHECKERS---*/
	
	public GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException{
		String[] l = line.trim().split("\\s+");
		if (l.length == 2 && this.matchesGObjName(l[1])) {
			this.pos = getPositionFrom(l);
			this.g = game;
			return this.createNewObject(this.createPosition(Direction.NONE), this.g);
		}
		else return null;
	}
	
	public abstract boolean matchesGObjName(String s);
	
	public boolean isSolid() { return true; }
	
	public boolean isAlive() { return this.alive; }
	
	public boolean isInAir(Position p) { return this.g.isInAir(p); }
	
	public boolean isInPosition(Position p) { return this.pos.equals(p); }

	
	/*---INTERACTIONS---*/
	
	public void interactWith(Lemming l) {}

	public void interactWith(Wall w) {}

	public void interactWith(ExitDoor ed) {}
	
	public void interactWith(MetalWall mW) {}
	
	public void parameterObjInteractWithOthers(GameItem obj) { g.parameterObjInteractWithOthers(obj);}
	
	public void resetInteraction() {}	
	
	
	/*---OTHERS---*/
	
	public void lemmingArrived() { this.g.lemmingArrived(); }
	
	public void lemmingDead() { this.g.lemmingDead(); }
	
	public static String toString (String[] s) {
		StringBuilder sb = new StringBuilder(); 
		
		for (int i = 0; i < s.length-1; ++i) {
			sb.append(s[i]).append(" ");
		}
		sb.append(s[s.length-1]); 
		 
		return sb.toString();
	}
	
	public static Position getPositionFrom(String[] line) throws ObjectParseException, OffBoardException {
		if (line[0].charAt(0) == '(' && line[0].charAt(line[0].length()-1) == ')') {
				int posComma = findCommaBetweenParentheses(line[0]);
				try {
					//Hacer un SubString es Cerrado por la izq. y Abierto por la dcha. [,)
					int fila = Integer.valueOf(line[0].substring(1, posComma));
					int col = Integer.valueOf(line[0].substring(posComma+1,line[0].length()-1));
					Position p = new Position(fila, col);
					if (p.isInBoard()) return p; 
				}
				catch (OffBoardException obe) {
						throw new OffBoardException(Messages.ERROR_OBJECT_POSITION_OFF_BOARD.formatted(toString(line)));
					}
				catch (NumberFormatException e) {
					throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(toString(line)));
				}
			}
		throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(toString(line)));
	}
	
	@Override
	public abstract String toString(); 
	
	
	/*---PRIVATE---*/
	
	private static int findCommaBetweenParentheses(String s) {
		int i = 1;
		while (i < s.length()-1 && s.charAt(i) != ',') ++i;
		
		if (i != s.length()) return i;
		else return -1;
	}
	
	
	

	/*El gameUpdate() ya no es necesario. El double-dispatch nos permite delegar la propia función de
	 actualizar el objeto en cuestión en función de la interacción que haga con otro objeto*/
}
