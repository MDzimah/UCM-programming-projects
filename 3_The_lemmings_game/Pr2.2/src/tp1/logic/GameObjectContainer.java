package tp1.logic;

import java.util.ArrayList;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.lemmingsRole.LemmingRole;

public class GameObjectContainer {
	private ArrayList<GameObject> gameObjects;
	
	public GameObjectContainer() {
		this.gameObjects = new ArrayList<GameObject>();
	}
	
	
	/*---GETTERS---*/
	
	protected int getNumGameObjects() { return this.gameObjects.size(); }
	
	
	/*---SETTERS---*/
	
	protected boolean setRole(LemmingRole r, Position p) { 
		for (GameObject gObj: gameObjects) {
			if(gObj.isInPosition(p) && gObj.setRole(r)) return true;
		}
		return false;
	}
	
	
	/*---CHECKERS---*/
	
	protected boolean isSolid(Position pos) {
		for (GameObject gObj: gameObjects) {
			/*Tiene que estar vivo porque, de lo contrario, el DownCaver no podría actualizar 
			 su posición a la pared que acaba de cavar*/
			if (gObj.isAlive() && gObj.isSolid() && gObj.isInPosition(pos)) return true;
		}
		return false;
	}
	
	
	/*---OTHERS---*/
	
	protected void add(GameObject g) { this.gameObjects.add(g); }
	
	//El método getIcon() es interno a cada objeto, entonces tienen ya el icono de Exit door, Wall o Lemming (dcha o izq) de por sí
	//Como un Lemmings y Exit doors pueden estar solapados, hay que meterlos en el StringBuilder s también si coinciden en posición
	protected String positionToString(Position pos) {
		
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.gameObjects.size(); ++i) {
			if (this.gameObjects.get(i).isInPosition(pos)) s.append(this.gameObjects.get(i).getIcon());
		}
		
		return s.toString();
	}
	
	protected void update() {
		for (GameObject gObj: gameObjects) {
			this.parameterObjInteractWithOthers(gObj);
		}
		
		//Para resetear el estado de interacción de los lemmings que han hecho alguna tarea distinta a "fall"
		for (GameObject gObj: gameObjects) {
			gObj.resetInteraction();
		}
		this.removeDead();
	}
	
	public void parameterObjInteractWithOthers(GameItem obj) {
		for (GameObject gObj: gameObjects) {
			gObj.receiveInteraction(obj);
		}
	}	
	
	
	/*---PRIVATE---*/
	
	private void removeDead() {
		for (int i = 0; i < this.gameObjects.size(); ++i) {
			if(!this.gameObjects.get(i).isAlive()) { 
				this.gameObjects.remove(i); 
				--i;
			}
		}
	}
}
