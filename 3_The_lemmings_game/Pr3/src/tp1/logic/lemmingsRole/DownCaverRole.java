package tp1.logic.lemmingsRole;

import tp1.view.Messages;
import tp1.logic.Direction;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;

public class DownCaverRole extends AbstractRole implements LemmingRole {
	private static final String NAME = Messages.DOWNCAVER_ROL_NAME;
	private static final String HELP = Messages.DOWNCAVER_ROL_HELP;
	private static final String ICON = Messages.LEMMING_DOWN_CAVER;
	private static final String SYMBOL = Messages.DOWNCAVER_ROL_SYMBOL;
	private boolean hasCaved = false;
	
	public DownCaverRole() {}
	
	//Idem (see WalkerRole)
	private DownCaverRole(boolean hasCaved) {
		this.hasCaved = hasCaved;
	}
	
	@Override
	public LemmingRole copy() {
		return new DownCaverRole(this.hasCaved);
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
	public void interactionWith(Wall w, Lemming owner) {
		if(!this.hasCaved) {
			if (owner.wallUnder(w.createPosition(Direction.NONE))) {
				w.dead();
				owner.fall();
				owner.resetFuerzaCaida();
				this.hasCaved = true;
				
			}
		}
	}
	
	public void resetInteraction(Lemming l) {
		/*If in this cycle the DownCaver lemming has done its task, the hasCaved is reseted.
		 Else, it has found an object that has not allowed it to do its task (for example, 
		 a metal wall)*/
		if(this.hasCaved) this.hasCaved = false;
		else l.disableRole();
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
}
