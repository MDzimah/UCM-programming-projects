package simulator.model;

import java.util.*;

import org.json.JSONObject;


public class TrafficSimulator {

    private RoadMap _roadMap;
    private Queue<Event> _events;
    private int _time;

    public TrafficSimulator() {
        this._roadMap = new RoadMap();
        this._events = new PriorityQueue<>();
        this._time = 0;
    }   
    
    /*SETTERS*/
    
    public void addEvent(Event e) { this._events.add(e); }
    
	public void advance() {
		++this._time;
		
		/*Looks at first element of the queue and removes it 
		if its time is <= to the actual time. It's <= because
		the TrafficSimulator can be reseted*/
		while (!this._events.isEmpty() && this._events.peek()._time <= this._time) {
			this._events.poll().execute(this._roadMap); 
		}
		
		for (Junction j: this._roadMap.getJunctions()) 
			j.advance(this._time);

		for(Road r: this._roadMap.getRoads())
			r.advance(this._time);
	}
	
	public void reset() { 
        this._roadMap = new RoadMap();
        this._events = new PriorityQueue<>();
        this._time = 0;
	}
    
	
    /*CHECKERS*/

	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("time", this._time);
		j.put("state", this._roadMap.report());
		return j;
	}	

	
}