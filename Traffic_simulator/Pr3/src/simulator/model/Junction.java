package simulator.model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import messages.Messages;

public class Junction extends SimulatedObject {
	
	private List<Road> incomingRoads;
	private Map<Junction,Road> outgoingRoads;
	private Map<Road,List<Vehicle>> incRoad_Queue; //To associate roads to queues
	private List<List<Vehicle>> queues;
	private int greenLightIndex;
	private int lastSwitchingTime;
	private LightSwitchingStrategy lss;
	private DequeuingStrategy ds;
	private int x, y;
	
	
	Junction(String id, LightSwitchingStrategy lss, DequeuingStrategy ds, int x, int y) {
		super(id);
		if (lss != null && ds != null && x >= 0 && y >= 0) {
			this.lss = lss;
			this.ds = ds;
			this.x = x;
			this.y = y;
			this.lastSwitchingTime = 0;
			this.greenLightIndex = -1;
			this.incomingRoads = new LinkedList<Road>();
			this.outgoingRoads = new HashMap<Junction,Road>();
			this.incRoad_Queue = new HashMap<Road,List<Vehicle>>();
			this.queues = new ArrayList<List<Vehicle>>();
		}
		else throw new IllegalArgumentException(Messages.INVALID_CLASS_ARGUMENTS.formatted("junction"));
	}
	
	/*SETTERS*/
	
	/*The advance is split into 2 sections, the first does the dequeuing strategy and the following 
	section finds the next greenlight so that next time it advances it will get the corresponding queue*/ 
	@Override
	void advance(int currTime) {
		//We check that a light is green and the queues are not empty
		if(this.greenLightIndex != -1 && !this.queues.isEmpty()) {
			/*We get the list of vehicles that are about to be removed by calling the corresponding 
			dequeuing strategy and passing it the queue that is green*/
			List<Vehicle> vehicles = this.ds.dequeue(queues.get(this.greenLightIndex));
			for(Vehicle v : vehicles) {
			    v.moveToNextRoad();
			    this.queues.get(greenLightIndex).remove(v);
			}
		}

		//This calls the corresponding light switching strategy and finds the next green light
		int prevGreen = this.greenLightIndex;
		this.greenLightIndex = this.lss.chooseNextGreen(this.incomingRoads, this.queues, this.greenLightIndex, this.lastSwitchingTime, currTime);
		
		//If the next greenlight is different to the previous then we must change the last switching time
		if(this.greenLightIndex != prevGreen) {
			this.lastSwitchingTime = currTime;
		}
	}
	
	void addIncomingRoad(Road r) {
		if(!r.getDest().equals(this)) throw new IllegalArgumentException(Messages.INVALID_OBJECT.formatted("road"));
		else {
			//List & queue are added, then they are associated in the map incRoad_Queue
			this.incomingRoads.add(r);
			
			LinkedList<Vehicle> newQueue = new LinkedList<Vehicle>(); 
			
			this.queues.add(newQueue);
			this.incRoad_Queue.put(r, newQueue);
		}
	}
	
	void addOutGoingRoad(Road r) {
		if(!this.equals(r.getSrc())) throw new IllegalArgumentException(Messages.INVALID_OBJECT.formatted("road"));
		else {
			//Puts in the map of junction-roads the destination junction
			if(!this.outgoingRoads.containsKey(r.getDest())) this.outgoingRoads.put(r.getDest(), r);
			else throw new IllegalArgumentException(Messages.INVALID_OBJECT_REASON.formatted("road","already present"));
		}
	}
	
	void enter(Vehicle v) { 
		this.incRoad_Queue.get(v.getRoad()).add(v);
	}
	
	
	/*GETTERS*/
	
	Road roadTo(Junction j) { return this.outgoingRoads.get(j); }
	public int getGreenLightIndex() { return this.greenLightIndex; }
	public List<Road> getInRoads() { return Collections.unmodifiableList(this.incomingRoads); }
	public Map<Road,List<Vehicle>> getInRoad_Queue() { return Collections.unmodifiableMap(this.incRoad_Queue); }
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	
	
	/*CHECKERS*/
	
	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", this._id);

		String s;
		if (this.greenLightIndex == -1 || this.incomingRoads.size() == 0) s = "none";
		else s = this.incomingRoads.get(this.greenLightIndex)._id;
		j.put("green", s);
		
		j.put("queues", this.reportQueues()); 
		return j;
	}
	
	
	/*PRIVATE*/
	
	private JSONArray reportQueues(){
		JSONArray jList = new JSONArray();
		
		for (Road r: this.incomingRoads) {
			JSONObject jo = new JSONObject();
			jo.put("road", r._id);
			
			JSONArray ja = new JSONArray();
			for (Vehicle v: this.incRoad_Queue.get(r)) {
				ja.put(v._id);
			}
			jo.put("vehicles", ja);
			jList.put(jo);
		}
		return jList;
	}
}
