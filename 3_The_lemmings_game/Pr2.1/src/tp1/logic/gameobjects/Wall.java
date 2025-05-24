package tp1.logic.gameobjects;

import tp1.logic.*;
import tp1.view.Messages;

public class Wall extends GameObject{
	public Wall(GameWorld g, Position p) {
		super(p, g);
	}
	
	//Nunca puede ser Exit door (si se pregunta por él con "isExit").
	protected boolean isExit(Position p) { 
		return false; 
	}
	
	//Puede que la posición pasada por parámetro no coincida con su atributo y, por ende, sea una posición vacía (o un Exit door)
	protected boolean isSolid(Position p) {
		return this.isInPosition(p); 
	}
	
	public void update() {}
	
	public String getIcon() {
		return Messages.WALL;
	}
}
