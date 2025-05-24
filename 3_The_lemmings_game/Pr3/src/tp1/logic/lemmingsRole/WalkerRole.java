package tp1.logic.lemmingsRole;

import tp1.view.Messages;
import tp1.logic.Direction;
import tp1.logic.Position;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;

public class WalkerRole extends AbstractRole implements LemmingRole {
	private static final String NAME = Messages.WALKER_ROL_NAME;
	private static final String HELP = Messages.WALKER_ROL_HELP;
	private static final String ICON_RIGHT = Messages.LEMMING_RIGHT;
	private static final String ICON_LEFT = Messages.LEMMING_LEFT;
	private static final String SYMBOL = Messages.WALKER_ROL_SYMBOL;
	private boolean hasWalked = false;

	public WalkerRole(){}
	
	/*Due to the fact that interfaces cannot be initialized, instead of having a 
	copy constructor, it has to be a method that copies the most primitive attributes
	of the class that implements the interface. Having done so, we have to return 
	a new object copy of the class taking advantage of polymorphism*/
	private WalkerRole(boolean hasWalked) {
		this.hasWalked = hasWalked;
	}
	
	@Override
	public LemmingRole copy() {
		return new WalkerRole(this.hasWalked);
	}

	
	/*---GETTERS---*/
	
	public String getSymbol() { return SYMBOL; }

	public String getHelp() { return HELP; }
	
	public String getIcon(Lemming l) {
		if (l.isDirRight()) return ICON_RIGHT;
		else return ICON_LEFT;
	}
	

	/*---CHECKERS---*/
	
	@Override
	public LemmingRole parse(String s) {
		if (s.equalsIgnoreCase(NAME) || s.equalsIgnoreCase(SYMBOL)) return this;
		else return null;
	}	

	
	/*---INTERACTIONS---*/
	
	@Override
	public void receiveInteraction(GameItem other, Lemming owner) {
		other.interactWith(owner);
	}

	@Override
	public void interactionWith(Wall w, Lemming owner) { this.wallsInteraction(w.createPosition(Direction.NONE), owner); }

	@Override
	public void interactionWith(ExitDoor ed, Lemming owner) {
		if(owner.createPosition(Direction.NONE).equals(ed.createPosition(Direction.NONE)) 
			&& !owner.isInAir(owner.createPosition(Direction.DOWN)) && owner.isAlive()){
			owner.dead();	
			owner.lemmingArrived();
			owner.resetNeedsToChangeDir();
			owner.resetNeedsToAdvance();
			this.hasWalked = true;
		}
	}
	
	@Override
	public void interactionWith(MetalWall mW, Lemming owner) { this.wallsInteraction(mW.createPosition(Direction.NONE), owner); }
	
	public void resetInteraction(Lemming l) {
		if(this.hasWalked) {
			this.hasWalked = false;
			if (l.needsToChangeDir()) {
				l.changeDir();
				l.resetNeedsToChangeDir();
			}
			else if(l.needsToAdvance()) {
				l.advance();
				l.resetNeedsToAdvance();
			}
		} 
		else l.fall(); //Has not interacted with a wall or anything whatsoever, therefore the lemming has to fall
	}
	
	
	/*---OTHERS---*/
	
	@Override
	public boolean canStart(Lemming l) {
		return true;
	}
	
	@Override
	public String toString() {
		return NAME;
	}

	
	/*---PRIVATE---*/
	
	private void wallsInteraction(Position p, Lemming l) {
		/*Just in case the exit door is first in the GameObjectContainer array (a lemming which interacts succesfully
		with an exit door is killed to later be removed, actualizing the arrived counter instead of the dead lemmings counter*/
		if(l.isAlive()) this.hasWalked = l.walk(p);
	}
}
