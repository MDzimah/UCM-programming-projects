package tp1.logic;

import tp1.logic.gameobjects.*;
import tp1.view.Messages;
import java.util.ArrayList;

public class GameObjectContainer {
	private ArrayList<Lemming> lemmings;

	private ArrayList<Wall> walls;

	private ArrayList<ExitDoor> exitDoors;

	GameObjectContainer() {
		this.lemmings = new ArrayList<Lemming>();
		this.walls = new ArrayList<Wall>();
		this.exitDoors = new ArrayList<ExitDoor>();
	}

	public int numLemsInBoard() {
		return this.lemmings.size();
	}
	public int numWs() {
		return this.walls.size();
	}
	public int numEd() {
		return this.exitDoors.size();
	}

	
	protected void add(Lemming l) {
		this.lemmings.add(l);
	}
	protected void add(Wall w) {
		this.walls.add(w);
	}
	protected void add(ExitDoor eD) {
		this.exitDoors.add(eD);
	}

	
	private int findWallWithPosX(Position p) {
		
		int i = 0;
		if(this.walls.size() > 0) {
			while (i < this.walls.size() && !this.walls.get(i).isInPosition(p)) {
				++i;
			}
		}
		return i;
	}
	
	private int findExitDoorWithPosX(Position p) {
		
		int i = 0;
		if (this.exitDoors.size()>0) {
			while (i < this.exitDoors.size() && !this.exitDoors.get(i).isInPosition(p)) {
				++i;
			}
			
		}
		return i;
	}
	
	
	public String positionToString(Position pos) {
		/*Dada una posición del mapa, hay una pared, un exit, uno o varios lemmings o está vacío. 
		Pero nunca pueden haber varios objetos en una posición (salvo si son lemmings o lemmings en
		puertas de salida). Todo ello se refleja con los if's que imposibilitan solapamientos indebidos*/
		
		boolean wall = true, eD = false, lem = false;
		StringBuilder stringOfLemmingsOrExitDoor = new StringBuilder();
		
		int i = this.findWallWithPosX(pos);
		if (i == this.walls.size()) {
			
			wall = false;
			eD = true;
			lem = true;
			
			int j = this.findExitDoorWithPosX(pos);
			
			for (int l = 0; l < this.lemmings.size(); ++l) {
				if (this.lemmings.get(l).isInPosition(pos)) {
					stringOfLemmingsOrExitDoor.append(this.lemmings.get(l).getIcon(this.lemmings.get(l)));
				}
			}
						
			if (stringOfLemmingsOrExitDoor.length() == 0)  {
				lem = false;
			}
			
			if (j < this.exitDoors.size()) stringOfLemmingsOrExitDoor.append(Messages.EXIT_DOOR);
			else eD = false;
		}
		
		if (wall) return Messages.WALL;
		else if (eD || lem) return stringOfLemmingsOrExitDoor.toString();
		else return Messages.EMPTY;
	}

	
	protected boolean isSolid(Position pos) {
		int i = this.findWallWithPosX(pos);
		return i != this.walls.size();
	}

	protected boolean isExit(Position pos) {
		int i = this.findExitDoorWithPosX(pos);
		return i != this.exitDoors.size();
	}
	
	private boolean removeDead(int index) {
		if (!this.lemmings.get(index).isAlive()) {
				this.lemmings.remove(index);
				return true;
		}
		else return false;
	}
	
	protected void removeDeadLems() {
		for (int i = 0; i < this.lemmings.size(); ++i) {
			if(this.removeDead(i)) --i;
		}
	}

	protected void removeLemsExit() {
		//Un lemming solo puede salir de un nivel si ha entrado andando por la puerta de salida, no si cae sobre la misma
		for (int i = 0; i < this.lemmings.size(); ++i) {
			if (this.lemmings.get(i).getFc() == 0 && this.lemmings.get(i).isOnExit()) {
				this.lemmings.remove(i);
				--i;
			}
		}
	}

	
	protected void update() {
		for (int i = 0; i < this.lemmings.size(); ++i) {
			this.lemmings.get(i).update();
		}
	}
}
