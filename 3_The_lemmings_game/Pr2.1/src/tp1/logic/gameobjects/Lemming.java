package tp1.logic.gameobjects;

import tp1.logic.*;

public class Lemming extends GameObject{
	private WalkerRole rol;
	private Direction dir;
	private int fuerzaCaida;
	
	public Lemming(GameWorld g, Position p){
		super(p, g);
		this.rol = new WalkerRole();
		this.fuerzaCaida = 0;
		this.dir = Direction.RIGHT;
	}
	
	//Un Lemming no es ni exit ni solid
	protected boolean isExit(Position p) { 
		return false; 
	}
	
	protected boolean isSolid(Position p) {
		return false; 
	}
	
	//Mira si la posicion p está en el mapa
	private boolean isInBoard(Position p) {
		return p.isInBoard();
	}
	
	private void changeDir()
	{
		if (this.dir.equals(Direction.RIGHT))
			this.dir = Direction.LEFT;
		else
			this.dir = Direction.RIGHT;
	}
	
	
	protected void walkOrFall() {	
		Position p = this.createPosition(Direction.DOWN); 
		
		//Debajo del lemming hay una posición válida del mapa
		if (this.isInBoard(p)) {
			//Se encuentra en el aire
			if (this.isInAir(p)) {
				this.changePosition(p);
				++this.fuerzaCaida;
			}
			//Se encuentra sobre un bloque
			else {
				//Lleva cayendo 3 o más filas (caída letal). Se actualiza el contador correspondiente en Game
				if(this.fuerzaCaida > 2) {
					this.dead();
					this.lemmingDead();
				}
				//Lleva cayendo menos de 3 filas (caída no letal) y aterriza o estaba caminando
				else {
					if (this.fuerzaCaida != 0) this.fuerzaCaida = 0;
					
					p = createPosition(this.dir);
					Position actualPos = createPosition(Direction.NONE);
					
					//Está en este ciclo sobre un Exit door y hay que actualizar el contador correspondiente en Game
					if (this.isOnExit(actualPos)) { 
						this.dead();	
						this.lemmingArrived();
					}
					
					//Caminar normal
					if (this.isInBoard(p) && this.isInAir(p)) { 
						this.changePosition(p);
					}
					//Hay una pared en su dirección en el próximo ciclo
					else changeDir();
				}
			}
		}
		//El lemming está sobre el vacío (cae fuera del mapa en el próximo ciclo). Se actualiza el contador correspondiente en Game
		else {
			this.dead();
			this.lemmingDead();
		}
	}
	
	@Override
	public String getIcon() {
		return this.rol.getIc(this.dir);
	}
	
	public void update() {
		this.rol.play(this);
	}
}
