package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.*;
import tp1.logic.lemmingsRole.LemmingRole;
import tp1.logic.lemmingsRole.LemmingRoleFactory;
import tp1.logic.lemmingsRole.WalkerRole;
import tp1.view.Messages;

public class Lemming extends GameObject{
	private LemmingRole rol;
	private Direction dir;
	private int fuerzaCaida = 0;
	//To hierarchize lemming-wall y lemming-exitDoor interactions
	private boolean needsToAdvance = false;
	private boolean needsToChangeDir = false;
	

	public Lemming(Position p, LemmingRole r, GameWorld g){
		super(p, g);
		this.rol = r;
		this.dir = Direction.RIGHT;
	}
	
	public Lemming() { super(); }
	
	//Copy constructor
	private Lemming(Lemming l, GameWorld g) {
		/*All game objects must have a reference to the same GameWorld
		 because if they don't, changes won't be done accordingly for every
		 game object*/
		super(l.createPosition(Direction.NONE), g);
		this.rol = l.rol.copy();
		
		if(l.isDirRight()) this.dir = Direction.RIGHT;
		else this.dir = Direction.LEFT;
		
		this.fuerzaCaida = l.fuerzaCaida;
	}
	
	@Override
	protected GameObject createNewObject(Position p, GameWorld g) {
		return new Lemming(this, g);
	}
	
	
	/*---GETTERS---*/

	@Override
	public String getIcon() { return this.rol.getIcon(this); }
	
	public int getFC() { return this.fuerzaCaida; }	
	
	public Direction getDir() {
		switch(this.dir.toString()) {
		case Messages.RIGHT: return Direction.RIGHT;
		case Messages.LEFT: return Direction.LEFT;
		default: return Direction.NONE;
		}
	}
	
	
	/*---SETTERS---*/
	
	@Override
	public boolean setRole(LemmingRole r) { 
		if(!this.rol.getSymbol().equals(r.getSymbol()) && r.canStart((this))) {
			this.rol = r.copy();
			return true;
		}
		else return false;
	}
	
	public void disableRole() { 
		this.rol = new WalkerRole(); 
		this.parameterObjInteractWithOthers(this);
		this.resetInteraction();
	}
	
	public void changeDir()
	{
		if (this.dir.equals(Direction.RIGHT))
			this.dir = Direction.LEFT;
		else
			this.dir = Direction.RIGHT;
	}
	
	public void advance() { this.changePosition(this.createPosition(this.dir)); }	

	public void resetFuerzaCaida() { this.fuerzaCaida = 0; }
	
	public void resetNeedsToAdvance() { this.needsToAdvance = false; }
	
	public void resetNeedsToChangeDir() { this.needsToChangeDir = false; }
	
	
	/*---CHECKERS---*/
	
	@Override
	public GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException {
		String[] l = line.trim().split("\\s+");
		if (l.length == 5 && this.matchesGObjName(l[1])) {
			Position posLem = getPositionFrom(l);
			
			this.changePosition(posLem);
			
			this.rol = getLemmingRoleFrom(l);
			
			this.dir = getLemmingDirectionFrom(l);
			
			this.fuerzaCaida = getLemmingHeigthFrom(l);
			
			return new Lemming(this,game);
		}
		else return null;
	}
	
	@Override
	public boolean matchesGObjName(String s) { 
		return s.equalsIgnoreCase(Messages.LEMMING_NAME) 
				|| s.equalsIgnoreCase(Messages.LEMMING_SHORTCUT); 
	}
	
	@Override
	public boolean isSolid() { return false; }
	
	public boolean isInAir(Position p) { return super.isInAir(p); }
	
	public boolean isDirRight() {
		if (this.dir.equals(Direction.RIGHT)) return true;
		else return false;
	}
	
	public boolean needsToAdvance() { return this.needsToAdvance; }
	
	public boolean needsToChangeDir() { return this.needsToChangeDir; }

	public boolean wallUnder(Position p_wall) { return this.createPosition(Direction.DOWN).equals(p_wall); }
	
	private boolean wallInFront(Position p_wall) { return this.createPosition(this.dir).equals(p_wall); }
	
	private boolean mapBoundariesInFront() throws OffBoardException { return !this.createPosition(this.dir).isInBoard(); }

	
	/*---INTERACTIONS---*/
	
	@Override
	public void receiveInteraction(GameItem other) { other.interactWith(this); }
	
	//FOR LEMMING-LEMMING INTERACTIONS
	/*
	@Override
	public void interactWith (Lemming l) { this.rol.interactionWith(l, this); }
	*/
	
	@Override
	public void interactWith (Wall w) { this.rol.interactionWith(w, this); }
	
	@Override
	public void interactWith (ExitDoor eD) { this.rol.interactionWith(eD, this); }
	
	@Override
	public void interactWith (MetalWall mW) { this.rol.interactionWith(mW, this); }
	
	@Override
	public void resetInteraction() { this.rol.resetInteraction(this); }

	
	/*---MOVEMENTS---*/
	
	public boolean walk(Position p_wallInteract) {
		//needsToChangeDir has priority over needsToAdvance
		if (!this.needsToChangeDir) {
			if (!this.isInAir(this.createPosition(Direction.DOWN))) {
				
				//Has been falling for 3 or more rows (fatal fall)
				if(this.fuerzaCaida > 2) {
					this.dead();
					this.lemmingDead();
					return true;
				}
				//Has been falling for less than 3 rows or has walked in the previous cycle
				else {
					if (this.fuerzaCaida != 0) this.resetFuerzaCaida();
					
					//There's a wall in its direction in the next cycle
					try {
						if (this.wallInFront(p_wallInteract) || this.mapBoundariesInFront()) {
							this.needsToChangeDir = true;
						}
						//Normal walking
						else if (this.wallUnder(p_wallInteract)) {
							/*The exit door (in which the lemming has to enter) could be further down 
							 the GameObjectContainer array. Therefore, it's necessary to do said interaction
							 instead of advancing or changing direction. If such interaction is not found, then
							 we can allow the lemming to advance/change direction*/
							this.needsToAdvance = true;
						}
					}
					//The left/right screen boundaries are in front of it in the next cycle
					catch(OffBoardException obe) { this.needsToChangeDir = true; } 
				}
			}
		}
		
		return this.needsToAdvance || this.needsToChangeDir;
	}

	public void fall() {
		Position p = this.createPosition(Direction.DOWN); 
		
		//Under the lemming there is a position inside the map
		try {
			if (p.isInBoard()) {
				//Lemming is in the air
				if (this.isInAir(p)) {
					this.changePosition(p);
					++this.fuerzaCaida;
					//return true; (if it's ever needed for anything)
				}
				//else return false;
			}
		}
		//The lemming is above the void (falls out of the map in the next cycle)
		catch (OffBoardException obe){
			this.dead();
			this.lemmingDead();
			//return true;
		}
	}
	
	
	/*---OTHERS---*/
	
	//For the SaveCommand
	@Override
	public String toString() {
		return this.createPosition(Direction.NONE).toString() + ' ' + Messages.LEMMING_NAME 
				+ ' ' + this.dir.toString() + ' ' + this.fuerzaCaida + ' ' + this.rol.toString();
	}
	
	/*---PRIVATE---*/
	
	//These methods are used to make sure the lemming read from the line of the file is valid
	
	private static LemmingRole getLemmingRoleFrom(String[] line) throws ObjectParseException {
		LemmingRole lr = LemmingRoleFactory.parse(line[4]);
		
		if (lr != null) return lr;
		else throw new ObjectParseException(Messages.INVALID_LEMMING_ROLE.formatted(toString(line)));

	}
	
	private static Direction getLemmingDirectionFrom(String[] line) throws ObjectParseException {	
		switch (line[2].toLowerCase()) {
		case Messages.RIGHT: return Direction.RIGHT;
		case Messages.LEFT: return Direction.LEFT;
		case Messages.UP: case Messages.DOWN: case Messages.NONE: throw new ObjectParseException(Messages.INVALID_LEMMING_DIRECTION.formatted(toString(line)));
		}
		throw new ObjectParseException(Messages.ERROR_OBJECT_DIRECTION.formatted(toString(line)));
	}
	
	private int getLemmingHeigthFrom(String[] line) throws ObjectParseException {
		try {
			int n = Integer.valueOf(line[3]);
			return n; 
		}
		catch (NumberFormatException e) {
			throw new ObjectParseException(Messages.INVALID_LEMMING_HEIGHT.formatted(toString(line)));
		}
	}
}
