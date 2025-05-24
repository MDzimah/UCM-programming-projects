package tp1.logic;

import java.util.ArrayList;

public class GameObjectContainer {
	private ArrayList<GameObject> gameObjects;
	
	public GameObjectContainer() {
		this.gameObjects = new ArrayList<GameObject>();
	}
	
	protected void add(GameObject g) { this.gameObjects.add(g); }
	
	int getNumGameObjects() { return this.gameObjects.size(); }
	
	//El método getIcon() es interno a cada objeto, entonces tienen ya el icono de Exit door, Wall o Lemming (dcha o izq) de por sí
	//Como un Lemmings y Exit doors pueden estar solapados, hay que meterlos en el StringBuilder s también
	protected String positionToString(Position pos) {
		
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.gameObjects.size(); ++i) {
			if (this.gameObjects.get(i).isInPosition(pos)) s.append(this.gameObjects.get(i).getIcon());
		}
		
		return s.toString();
	}
	
	//Al principio del juego hay que saber cuántos lemmings hay en pantalla. Si el objeto no es ni sólido ni exit, entonces tiene que ser un Lemming
	protected int numLemsStart() {
		int cont = 0;
		for (GameObject gObj: gameObjects) {
			Position p = gObj.createPosition(Direction.NONE);
			if (!gObj.isSolid(p) && !gObj.isExit(p))
				++cont;
		}
		
		return cont;
	}
	
	protected boolean isSolid(Position pos) {
		for (GameObject gObj: gameObjects) {
			if (gObj.isSolid(pos)) return true;
		}
		return false;
	}
	
	protected boolean isExit(Position pos) {
		for (GameObject gObj: gameObjects) {
			if (gObj.isExit(pos)) return true;
		}
		return false;
	}
	
	private void removeDead() {
		for (int i = 0; i < this.gameObjects.size(); ++i) {
			if(!this.gameObjects.get(i).isAlive()) { 
				this.gameObjects.remove(i); 
				--i;
			}
		}
	}
	
	protected void update() {
		for (int i = 0; i < this.gameObjects.size(); ++i) {
			this.gameObjects.get(i).update();
		}
		removeDead();
	}
}
