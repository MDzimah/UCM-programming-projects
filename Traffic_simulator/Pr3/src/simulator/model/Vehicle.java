package simulator.model;

import java.util.*;

import org.json.JSONObject;

import messages.Messages;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle> {

	private List<Junction> itinerary = new ArrayList<Junction>();
	private int itineraryIndex;
	private final int maxSpeed;
	private int actualSpeed;
	private VehicleStatus state;
	private Road road;
	private int location;
	private int contClass;
	private int totalContamination;
	private int totalDistance;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if(maxSpeed > 0 && contClass >= 0 && contClass <= 10 && itinerary.size() > 1) {
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
			this.itineraryIndex = 0;
			this.totalContamination = this.totalDistance = this.actualSpeed = this.location = 0;
			this.state = VehicleStatus.PENDING;
		} 
		else throw new IllegalArgumentException (Messages.INVALID_CLASS_ARGUMENTS.formatted("vehicle"));
	}
	
	
	/*GETTERS*/
	
	public int getLocation() { return this.location; }
	public int getTotalDistance() { return this.totalDistance; }
	public int getSpeed() { return this.actualSpeed; }
	public int getItIndex() { return this.itineraryIndex; }
	public int getMaxSpeed() { return this.maxSpeed; }
	public int getContClass() { return this.contClass; }
	public VehicleStatus getStatus() { return this.state; }
	public int getTotalCO2() { return this.totalContamination; }
	public List<Junction> getItinerary() { return Collections.unmodifiableList(new ArrayList<>(itinerary)); }
	public Road getRoad() { return this.road; }
	
	
	/*SETTERS*/
			
	void setSpeed(int s) {
		if (s < 0) throw new IllegalArgumentException (Messages.INVALID_OBJECT_REASON.formatted("Speed", "negative"));
		else {
			if (this.state.equals(VehicleStatus.TRAVELING)) this.actualSpeed = Integer.min(this.maxSpeed, s);
		}
	}
	
	void setContClass(int c) {
		if (c < 0 || c > 10) throw new IllegalArgumentException (Messages.NOT_IN_VALID_RANGE.formatted("Contamination class","0","10"));
		else this.contClass = c;
	}
	
	void advance(int currTime) {
		if (this.state.equals(VehicleStatus.TRAVELING)) {
			int oldLocation = this.location;
			this.location = Integer.min(this.location + this.actualSpeed, this.road.getLength());
			int d_traveled = this.location - oldLocation;
			this.totalDistance += d_traveled;
			
			int c = d_traveled*this.contClass;
			this.totalContamination += c;
			this.road.addContamination(c);
			
			//Vehicle is added to the the junction at the road's end
			if (this.location >= this.road.getLength()) {
				this.state = VehicleStatus.WAITING;
				this.actualSpeed = 0;
				this.road.getDest().enter(this);
			}
		}
	}

	void moveToNextRoad()  {
		if (this.state == VehicleStatus.PENDING || this.state == VehicleStatus.WAITING) {			
			//Vehicle leaves the road and the junction it leads into
			if(this.state == VehicleStatus.WAITING) this.road.exit(this);

			/*The vehicle enters the road that connects the previous junction to the next one 
			of its itinerary (if it exists). Therefore we have to find said road using the method 
			"roadTo" of the Junction class. When found, the vehicle can enter it*/
			if(this.itineraryIndex + 1 < this.itinerary.size()) {
				Junction nextJunction = this.itinerary.get(this.itineraryIndex + 1);
				this.road = this.itinerary.get(this.itineraryIndex).roadTo(nextJunction);
				this.actualSpeed = this.location = 0;
				this.road.enter(this);
				
				//Vehicle needs updating for the new road
				this.state = VehicleStatus.TRAVELING;
				this.setSpeed(this.road.calculateVehicleSpeed(this));
				++this.itineraryIndex;
			}	
			//Car is waiting at final junction (or pending with 1 junction in the itinerary) and has ended its itinerary
			else {
				this.state = VehicleStatus.ARRIVED; 
				this.road.exit(this);
				this.road = null;
			}
		}
		else throw new IllegalArgumentException(Messages.INVALID_OBJECT.formatted("vehicle status in junction"));
	}
	
	
	/*CHECKERS*/
	
	@Override
	public int compareTo(Vehicle o) { return -Integer.compare(this.location,o.location); } //"Minus" added to ensure descending ordering
	
	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", this._id);
		j.put("speed", this.actualSpeed);
		j.put("distance", this.totalDistance);
		j.put("co2", this.totalContamination);
		j.put("class", this.contClass);
		j.put("status", this.state.toString());
		if(this.road != null) {
			j.put("road", this.road._id);
			j.put("location", this.location);
		}
		return j;
	}
}
