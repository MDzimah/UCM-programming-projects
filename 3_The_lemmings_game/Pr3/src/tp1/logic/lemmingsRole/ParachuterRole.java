package tp1.logic.lemmingsRole;

import tp1.logic.Direction;
import tp1.logic.Position;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;
import tp1.view.Messages;

public class ParachuterRole extends AbstractRole implements LemmingRole {
	private static final String NAME = Messages.PARACHUTER_ROL_NAME;
	private static final String HELP = Messages.PARACHUTER_ROL_HELP;
	private static final String ICON = Messages.LEMMING_PARACHUTE;
	private static final String SYMBOL = Messages.PARACHUTER_ROL_SYMBOL;
	private boolean hasLanded = false;
	
    public ParachuterRole() {}
    
    //Idem (see WalkerRole)
    private ParachuterRole(boolean hasWalked) {
		this.hasLanded = hasWalked;
	}
	
	@Override
	public LemmingRole copy() {
		return new ParachuterRole(this.hasLanded);
	}
    
	
    /*---GETTERS---*/
    
    public String getSymbol() { return SYMBOL; }

	public String getHelp() { return HELP; }
   
	public String getIcon(Lemming l) { return ICON; }
	
	
	 /*---CHECKERS---*/
	
	@Override
	public LemmingRole parse(String s) {
		if (s.equalsIgnoreCase(NAME) || s.equalsIgnoreCase(SYMBOL)) return this;
		else return null;
	}
	
	
	/*---INTERACTIONS---*/
	
	@Override
	public void receiveInteraction(GameItem other, Lemming owner) { other.interactWith(owner); }

	@Override
	public void interactionWith(Wall w, Lemming owner) { this.wallsInteraction(w.createPosition(Direction.NONE), owner); }
	
	@Override
	public void interactionWith(MetalWall mW, Lemming owner) { this.wallsInteraction(mW.createPosition(Direction.NONE), owner); }
	
	public void resetInteraction(Lemming l) {
		/*If in this cycle the Parachuter lemming hasn't done its task, the interaction is reseted and its role is disabled.
		Else, the lemming hasn't found any object that hasn't allowed it to fall (to do it's purpose)*/
		if(this.hasLanded) {
			this.hasLanded = false;
			l.disableRole();
		}
		else {
			l.fall();
			l.resetFuerzaCaida();
		}
	}
	
	
	/*---OTHERS---*/
	
	public boolean canStart(Lemming l) {
		return true;
	}
	
	@Override
	public String toString() {
		return NAME;
	}
	
	
	/*---PRIVATE---*/
	
	private void wallsInteraction(Position p, Lemming l) {
		if(!this.hasLanded && l.wallUnder(p)) {
			l.resetFuerzaCaida();
			this.hasLanded = true;
		}
	}
	
}
