package tp1.logic.gameobjects;
import tp1.view.Messages;
import tp1.logic.Direction;

public class WalkerRole {
	
	public WalkerRole(){}
	
	protected void play(Lemming l) {
		l.walkOrFall();
	}
	
	protected String getIc(Direction d) {
		if (d.equals(Direction.LEFT)) return Messages.LEMMING_LEFT;
		else if(d.equals(Direction.RIGHT)) return Messages.LEMMING_RIGHT;
		else return Messages.EMPTY;
	}
}
