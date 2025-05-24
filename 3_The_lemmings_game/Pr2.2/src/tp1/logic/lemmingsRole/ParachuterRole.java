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
 
    
    /*---OTHERS---*/
    
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
	public void interactionWith(Wall w, Lemming owner) { this.wallsInteraction(w.createPosition(Direction.NONE), owner); }
	
	@Override
	public void interactionWith(MetalWall mW, Lemming owner) { this.wallsInteraction(mW.createPosition(Direction.NONE), owner); }
	
	public void resetInteraction(Lemming l) {
		/*En este ciclo el lemming NO ha hecho su tarea, se resetea para el próximo.
		En caso contrario, NO se ha encontrado un objeto que le ha impedido realizar 
		su rol ("parachute" (que es caer)) y, por ende, se deshabilita*/
		if(this.hasLanded) {
			this.hasLanded = false;
			l.disableRole();
		}
		else {
			l.fall();
			l.resetFuerzaCaida();
		}
	}
	
	
	/*---PRIVATE---*/
	
	private void wallsInteraction(Position p, Lemming l) {
		if(!this.hasLanded && l.wallUnder(p)) {
				l.resetFuerzaCaida();
				this.hasLanded = true;
		}
	}
	
}
