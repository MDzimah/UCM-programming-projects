package tp1.logic.gameobjects;

import tp1.logic.*;
import tp1.logic.lemmingsRole.LemmingRole;
import tp1.logic.lemmingsRole.WalkerRole;

public class Lemming extends GameObject{
	private LemmingRole rol = new WalkerRole();
	private Direction dir = Direction.RIGHT;
	private int fuerzaCaida = 0;
	//Sirven para jerarquizar las interacciones lemming-wall y lemming-exitDoor
	private boolean needsToAdvance = false;
	private boolean needsToChangeDir = false;
	
	public Lemming(Position p, LemmingRole r, GameWorld g){
		super(p, g);
		this.rol = r;
		this.increaseLemmingOnBoardCounter();
	}
	
	
	/*---GETTERS---*/
	
	@Override
	public String getIcon() { return this.rol.getIcon(this); }
	
	public int getFC() { return this.fuerzaCaida; }	
	
	
	/*---SETTERS---*/
	
	@Override
	public boolean setRole(LemmingRole r) { 
		if(!this.rol.getSymbol().equals(r.getSymbol())) {
			this.rol = r;
			return true;
		}
		else return false;
	}
	
	public void disableRole() { 
		this.rol = new WalkerRole(); 
		this.parameterObjInteractWithOthers(this);
		this.resetInteraction();
	}
	
	public void changeDir()
	{
		if (this.dir.equals(Direction.RIGHT))
			this.dir = Direction.LEFT;
		else
			this.dir = Direction.RIGHT;
	}
	
	public void advance() { this.changePosition(this.createPosition(this.dir)); }	

	public void resetFuerzaCaida() { this.fuerzaCaida = 0; }
	
	public void resetNeedsToAdvance() { this.needsToAdvance = false; }
	
	public void resetNeedsToChangeDir() { this.needsToChangeDir = false; }
	
	
	/*---CHECKERS---*/
	
	@Override
	public boolean isSolid() { return false; }
	
	public boolean isInAir(Position p) { return super.isInAir(p); }
	
	public boolean isDirRight() {
		if (this.dir.equals(Direction.RIGHT)) return true;
		else return false;
	}
	
	public boolean needsToAdvance() { return this.needsToAdvance; }
	
	public boolean needsToChangeDir() { return this.needsToChangeDir; }

	public boolean wallUnder(Position p_wall) { return this.createPosition(Direction.DOWN).equals(p_wall); }
	
	private boolean wallInFront(Position p_wall) { return this.createPosition(this.dir).equals(p_wall); }
	
	private boolean mapBoundariesInFront() { return !this.createPosition(this.dir).isInBoard(); }

		
	/*---INTERACTIONS---*/
	
	@Override
	public void receiveInteraction(GameItem other) { other.interactWith(this); }
	
	@Override
	public void interactWith (Wall w) { this.rol.interactionWith(w, this); }
	
	@Override
	public void interactWith (ExitDoor eD) { this.rol.interactionWith(eD, this); }
	
	@Override
	public void interactWith (MetalWall mW) { this.rol.interactionWith(mW, this); }
	
	@Override
	public void resetInteraction() { this.rol.resetInteraction(this); }

	
	/*---MOVEMENTS---*/
	
	public boolean walk(Position p_wallInteract) {
		//needsToChangeDir tiene prioridad sobre needsToAdvance
		if (!this.needsToChangeDir) {
			if (!this.isInAir(this.createPosition(Direction.DOWN))) {
				//Lleva cayendo 3 o más filas (caída letal). Se actualiza el contador correspondiente en Game
				if(this.fuerzaCaida > 2) {
					this.dead();
					this.lemmingDead();
					return true;
				}
				//Lleva cayendo menos de 3 filas (caída no letal) y aterriza o estaba caminando
				else {
					if (this.fuerzaCaida != 0) this.resetFuerzaCaida();
					
					//Hay una pared en su dirección en el próximo ciclo
					if (this.mapBoundariesInFront() || this.wallInFront(p_wallInteract)) {
						this.needsToChangeDir = true;
					}
					//Caminar normal
					else if (this.wallUnder(p_wallInteract)) {
						/*Podría ocurrir que el exitDoor está más adelante en el array de GameObjects.
						 Hay que asegurar de que si esto ocurre, la interacción con el wall se haga al final,
						 de ahí que no se haga "this.advance()" inmediatamente*/
						this.needsToAdvance = true;
					}
				}
			}
		}
		
		return this.needsToAdvance || this.needsToChangeDir;
	}

	public void fall() {
		Position p = this.createPosition(Direction.DOWN); 
		
		//Debajo del lemming hay una posición válida del mapa
		if (p.isInBoard()) {
			//Se encuentra en el aire
			if (this.isInAir(p)) {
				this.changePosition(p);
				++this.fuerzaCaida;
				//return true;
			}
			//else return false;
		}
		//El lemming está sobre el vacío (cae fuera del mapa en el próximo ciclo). Se actualiza el contador correspondiente en Game
		else {
			this.dead();
			this.lemmingDead();
			//return true;
		}
	}

}
