package tp1.logic.gameobjects;

import tp1.logic.*;
import tp1.view.Messages;

public class ExitDoor extends GameObject{
	
	public ExitDoor(GameWorld g, Position p) {
		super(p, g);
	}
	
	//Puede que la posición pasada por parámetro no coincida con su atributo y, por ende, sea una posición vacía (o un wall)
	protected boolean isExit(Position p) { 
		return this.isInPosition(p); 
	}
	 
	//Nunca puede ser sólido (si se pregunta por él con "isSolid").
	protected boolean isSolid(Position p) {
		return false; 
	}
	
	public void update() {}

	public String getIcon() {
		return Messages.EXIT_DOOR;
	}
}
