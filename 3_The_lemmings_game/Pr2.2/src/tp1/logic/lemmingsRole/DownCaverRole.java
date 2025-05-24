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
	 
	public String getIcon(Lemming l) { return ICON; }	
	
	
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
		/*En este ciclo el lemming ha hecho su tarea, se resetea para el próximo.
		En caso contrario, se ha encontrado un objeto que le ha impedido realizar 
		su rol y, por ende, se deshabilita*/
		if(this.hasCaved) this.hasCaved = false;
		else l.disableRole();
	}
}
