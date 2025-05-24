package tp1.logic.gameobjects;

import tp1.logic.*;
import tp1.view.Messages;

public class Lemming {
	private Game g;	
	private WalkerRole rol;
	private Position pos;
	private boolean vivo;
	private boolean onExit;
	private Direction dir;
	private int fuerzaCaida;
	
	public Lemming(Game g, Position p, WalkerRole r){
		this.g = g;
		this.pos = p; 
		this.rol = r;
		this.vivo = true;
		this.onExit = false;
		this.fuerzaCaida = 0;
		this.dir = Direction.RIGHT;
	}
	
	
	public int getFc() {
		return this.fuerzaCaida;
	}
	public boolean isAlive() {
		return this.vivo;
	}
	public boolean isOnExit() {
		return this.onExit;
	}
	
	
	private boolean isInAir(Position p) { 
		return this.g.isInAir(p);
	}
	
	//Mira si la posicion p está en el mapa
	private boolean isInBoard(Position p) {
		return p.isInBoard();
	}
	
	public boolean isInPosition(Position p) {
		return this.pos.equals(p);
	}
	
	
	private void changeDir()
	{
		if (this.dir.equals(Direction.RIGHT))
			this.dir = Direction.LEFT;
		else
			this.dir = Direction.RIGHT;
	}
	
	
	protected void walkOrFall() {	
		Position p = new Position(this.pos, Direction.DOWN);
		
		//Debajo del lemming hay una posición válida del mapa
		if (this.isInBoard(p)) {
			//Se encuentra en el aire
			if (this.isInAir(p)) {
				this.pos = new Position(this.pos, Direction.DOWN);
				++this.fuerzaCaida;
			}
			//Se encuentra sobre un bloque
			else {
				//Lleva cayendo 3 o más filas (caída letal)
				if(this.fuerzaCaida > 2) {
					this.vivo = false;
				}
				//Lleva cayendo menos de 3 filas (caída no letal) y aterriza (caso I) o estaba caminando (caso II)
				else {
					if (this.fuerzaCaida != 0) this.fuerzaCaida = 0;
					
					p = new Position(this.pos, this.dir);
					
					if (this.isInBoard(p) && this.isInAir(p)) { 
						if(this.g.isExit(this.pos)) { 
							this.onExit = true;
						}
						this.pos = p;
					}
					else changeDir();
				}
			}
		}
		//El lemming está sobre el vacío (cae fuera del mapa en el próximo ciclo)
		else this.vivo = false;
	}
	
	
	public String getIcon(Lemming l) {
		return this.rol.getIc(l.dir);
	}
	
	public void update() {
		this.rol.play(this);
	}
	
	//public String toString() {
	//	return '(' + this.pos.fila() + ',' + this.pos.columna() 
	//	+ ") L " + this.dir + ' ' + this.fuerzaCaida + ' ';
	//
}
