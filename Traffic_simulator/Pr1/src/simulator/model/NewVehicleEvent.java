package simulator.model;

import java.util.*;

public class NewVehicleEvent extends Event {
	
	private List<String> itinerary;
	private String id;
	private int maxSpeed;
	private int contClass;
	
    public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
        super(time);
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.contClass = contClass;
        this.itinerary = new ArrayList<String>(itinerary);
    }

	@Override
	void execute(RoadMap map)  {
		List<Junction> temp = new ArrayList<Junction>();
		for (String s: this.itinerary) {
			/*Looks for the junction associated to each junction id of the itinerary
			Then adds each of the junctions to the vehicle's itinerary*/
			temp.add(map.getJunction(s));
		}
		
		Vehicle v = new Vehicle(this.id,this.maxSpeed,this.contClass,new ArrayList<Junction>(temp));
		map.addVehicle(v);
		
		//To move vehicle into the first road of its itinerary
		v.moveToNextRoad();
	}
}
