package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import messages.Messages;

import java.util.*;

public abstract class Road extends SimulatedObject {

	private Junction beginningIntersection, endingIntersection;
	private int length;
	private final int maxSpeed;
	private int actualMaxSpeed;
	private int maxCont;
	private Weather weatherCond;
	private int totalContamination;
	private List<Vehicle> vehicles;
	
	Road(String id, Junction bI, Junction eI, int maxSpeed, int maxCont, int length, Weather w) {
		super(id);
		if (maxSpeed > 0 && maxCont > 0 && length > 0 && bI != null && eI != null && w != null) {
			this.maxSpeed = this.actualMaxSpeed = maxSpeed;
			this.maxCont = maxCont;
			this.length = length;
			this.beginningIntersection = bI;
			this.endingIntersection = eI;
			this.beginningIntersection.addOutGoingRoad(this); //Adds "This"(Road) as the exit of the beginning intersection
			this.endingIntersection.addIncomingRoad(this); //Adds "This"(Road) as the entrance of the ending intersection
			this.weatherCond = w;
			this.vehicles = new ArrayList<Vehicle>();
		}
		else throw new IllegalArgumentException(Messages.INVALID_CLASS_ARGUMENTS.formatted("road"));	
	}
	
	
	/*SETTERS*/
	
	void addContamination(int c) {
		if (c < 0) throw new IllegalArgumentException (Messages.INVALID_OBJECT_REASON.formatted("Added contamintion", "negative"));
		else this.totalContamination += c;
	}
	
	void enter(Vehicle v) {
		if (v.getLocation() != 0 || v.getSpeed() != 0)  throw new IllegalArgumentException (Messages.INVALID_OBJECT.formatted("vehicle"));
		else this.vehicles.add(v);
	}
	
	void exit(Vehicle v) { this.vehicles.remove(v);	}
	
	void setWeather(Weather w) {
		if (w == null)  throw new IllegalArgumentException (Messages.INVALID_OBJECT.formatted("weather"));
		else this.weatherCond = w;
	}
	
	void setActualMaxSpeed(int s) { this.actualMaxSpeed = s; }
	
	void setTotalCO2(int c) { this.totalContamination = c; }
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);

	
	/*GETTERS*/
	
	public int getLength() { return this.length; }
	public Junction getDest() { return this.endingIntersection; }
	public Junction getSrc() { return this.beginningIntersection; }
	public Weather getWeather() { return this.weatherCond; }
	public int getContLimit() { return this.maxCont; }
	public int getMaxSpeed() { return this.maxSpeed; }
	public int getTotalCO2() { return this.totalContamination; }
	public int getSpeedLimit() { return this.actualMaxSpeed; }
	public List<Vehicle> getVehicles() { return Collections.unmodifiableList(this.vehicles); }
	
	
	/*CHECKERS*/
	
	@Override
	void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		
		//Advances the vehicles in its road
		for (Vehicle v: this.vehicles) {
			v.setSpeed(this.calculateVehicleSpeed(v));
			v.advance(time);
		}
		Collections.sort(this.vehicles);
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", this._id);
		j.put("speedlimit", this.actualMaxSpeed);
		j.put("weather", this.weatherCond.toString());
		j.put("co2", this.totalContamination);
		j.put("vehicles", this.reportVehicles());
		return j;
	}

	
	/*PRIVATE*/
	
	private JSONArray reportVehicles(){
		JSONArray jList = new JSONArray();
		
		for (Vehicle v: this.vehicles)
			jList.put(v._id);
		
		return jList;
	}
}
