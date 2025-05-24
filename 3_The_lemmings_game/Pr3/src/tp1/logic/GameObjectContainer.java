package tp1.logic;

import java.util.ArrayList;

import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.lemmingsRole.LemmingRole;
import tp1.view.Messages;

//Manages all operations between game objects. Note that these are never specifically identified in any part of the code 
class GameObjectContainer {
	private ArrayList<GameObject> gameObjects;
	
	GameObjectContainer() {
		this.gameObjects = new ArrayList<GameObject>();
	}
	
	//Cloning constructor
	GameObjectContainer(GameObjectContainer GOCtoCopy, GameWorld g) {
		this.gameObjects = new ArrayList<GameObject>(); 
		for (GameObject gObj : GOCtoCopy.gameObjects) {
			this.gameObjects.add(gObj.createNewObject(gObj.createPosition(Direction.NONE), g));
		}
	}
	
	
	/*---GETTERS---*/
	
	int getNumGameObjects() { return this.gameObjects.size(); }
	
	
	/*---SETTERS---*/
	
	boolean setRole(LemmingRole r, Position p) throws OffBoardException { 
		for (GameObject gObj: gameObjects) {
			if(gObj.isInPosition(p) && gObj.setRole(r)) return true;
		}
		throw new OffBoardException(Messages.COMMAND_INCORRECT_SETROLE.formatted(p.toString(), r.toString()));
	}
	
	
	/*---CHECKERS---*/
	
	boolean isSolid(Position pos) {
		for (GameObject gObj: gameObjects) {
			//Has to be alive. If not, DownCaver wouldn't be able to actualize its position to the wall under it
			if (gObj.isAlive() && gObj.isSolid() && gObj.isInPosition(pos)) return true;
		}
		return false;
	}
	
	
	/*---OTHERS---*/
	
	void add(GameObject gObj) { this.gameObjects.add(gObj); }
	
	
	String positionToString(Position pos) {
		//getIcon() is internal to each object
		//Keep in mind that lemmings or exit doors can overlap
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.gameObjects.size(); ++i) {
			if (this.gameObjects.get(i).isInPosition(pos)) s.append(this.gameObjects.get(i).getIcon());
		}
		
		return s.toString();
	}
	
	void update() {
		for (GameObject gObj: gameObjects) {
			this.parameterObjInteractWithOthers(gObj);
		}
		
		//Preparation for the next cycle
		for (GameObject gObj: gameObjects) 
			gObj.resetInteraction();
		
		this.removeDead();
	}
	
	 void parameterObjInteractWithOthers(GameItem obj) {
		for (GameObject gObj: gameObjects) {
			gObj.receiveInteraction(obj);
		}
	}	
	
	//Used to save the game configuration into a file (see "configurations" file for the format)
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (GameObject obj : gameObjects) {
			sb.append(obj.toString()).append(Messages.LINE_SEPARATOR);
		}
		return sb.toString();
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
