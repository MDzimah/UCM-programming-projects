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
	
	
	/*---OTHERS---*/
	
	@Override
	public void start(Lemming l) {
		l.setRole(this);
	}
	
	@Override
	public LemmingRole parse(String s) {
		if (s.equalsIgnoreCase(this.getName()) || s.equalsIgnoreCase(this.getSymbol())) return this;
		else return null;
	}
	
	
	/*---GETTERS---*/
	
	private String getName() { return NAME; }
	
	public String getSymbol() { return SYMBOL; }

	public String getHelp() { return HELP; }
	
	public String getIcon(Lemming l) {
		if (l.isDirRight()) return ICON_RIGHT;
		else return ICON_LEFT;
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
			this.hasWalked = false; //Se resetea para el próximo ciclo
			if (l.needsToChangeDir()) {
				l.changeDir();
				l.resetNeedsToChangeDir();
			}
			else if(l.needsToAdvance()) {
				l.advance();
				l.resetNeedsToAdvance();
			}
		} 
		else l.fall(); //No ha interactuado con ningún objeto dado su walkerRole. Por ende, ha de caer.
	}
	

	/*---PRIVATE---*/
	
	private void wallsInteraction(Position p, Lemming l) {
		//Por si el exitDoor está primero en el array de GameObjects
		if(l.isAlive()) this.hasWalked = l.walk(p);
	}


}
