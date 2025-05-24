package simulator.model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import messages.Messages;

public class RoadMap {
	
	private List<Junction> jL;
	private List<Road> rL;
	private List<Vehicle> vL;
	private Map<String, Junction> jM;
	private Map<String, Road> rM;
	private Map<String, Vehicle> vM;
	
	RoadMap(){
		this.jL = new ArrayList<Junction>();
		this.rL = new ArrayList<Road>();
		this.vL = new ArrayList<Vehicle>();
		this.jM = new HashMap<String, Junction>();
		this.rM = new HashMap<String, Road>();
		this.vM = new HashMap<String, Vehicle>();
	}

	/*SETTERS*/
	
	void addJunction(Junction j) {
		if(!this.jM.containsKey(j._id)) {
			this.jL.add(j);
			this.jM.put(j._id,j);
		}
		else throw new IllegalArgumentException(Messages.INVALID_OBJECT_REASON.formatted("Junction", "already present"));
	}
	
	void addRoad(Road r) {
		if(!this.rM.containsKey(r._id) 
				&& this.jM.containsKey(r.getDest()._id) 
				&& this.jM.containsKey(r.getSrc()._id)) 
		{
			this.rL.add(r);
			this.rM.put(r._id,r);
		}
		else throw new IllegalArgumentException(Messages.INVALID_OBJECT_REASON.formatted("Road", "not connected to a Junction or already exists"));
	}
	
	void addVehicle(Vehicle v) {
		if(!this.vM.containsKey(v._id) && this.validItinerary(v)) 
		{
			this.vL.add(v);
			this.vM.put(v._id,v);
		}
		else throw new IllegalArgumentException(Messages.INVALID_OBJECT_REASON.formatted("Vehicle", "already present or doesn't have valid itinerary"));
	}
	
	void reset() {
		this.jL = new ArrayList<Junction>();
		this.rL = new ArrayList<Road>();
		this.vL = new ArrayList<Vehicle>();
		this.jM = new HashMap<String, Junction>();
		this.rM = new HashMap<String, Road>();
		this.vM = new HashMap<String, Vehicle>();
	}
	
	
	/*GETTERS*/
	
	public Junction getJunction(String id) { 
		if (this.jM.containsKey(id)) return this.jM.get(id);
		else return null;
	}
	
	public Road getRoad(String id) { 
		if (this.rM.containsKey(id)) return this.rM.get(id);
		else return null;
	}
	
	public Vehicle getVehicle(String id) { 
		if (this.vM.containsKey(id)) return this.vM.get(id);
		else return null;
	}
	
	public List<Junction> getJunctions() { return Collections.unmodifiableList(new ArrayList<>(this.jL)); }

	public List<Road> getRoads() { return Collections.unmodifiableList(new ArrayList<>(this.rL)); }

	public List<Vehicle> getVehicles() { return Collections.unmodifiableList(new ArrayList<>(this.vL)); }

	
	/*CHECKERS*/
	
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("junctions", this.reportJunctions());
		j.put("roads", this.reportRoads());
		j.put("vehicles", this.reportVehicles());
		return j;
	}
	
	
	/*PRIVATE*/
	
	private boolean validItinerary(Vehicle v) {
		int i = 0; boolean res = true; 
		
		while (i < v.getItinerary().size()-1 && res) {
			if (!rM.containsKey(v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1))._id)) res = false;
			++i;
		}
		return res;
	}
	
	private JSONArray reportJunctions(){
		JSONArray jList = new JSONArray();
		
		for (Junction j: this.jL)
			jList.put(j.report());
		
		return jList;
	}
	
	private JSONArray reportRoads(){
		JSONArray jList = new JSONArray();
		
		for(Road r: this.rL)
			jList.put(r.report());
		
		return jList;
	}
	
	private JSONArray reportVehicles(){
		JSONArray jList = new JSONArray();
		
		for (Vehicle v: this.vL)
			jList.put(v.report());
		
		return jList;
	}
}
