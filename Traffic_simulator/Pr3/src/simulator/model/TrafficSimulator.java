package simulator.model;

import java.util.*;

import org.json.JSONObject;


public class TrafficSimulator implements Observable<TrafficSimObserver>{
    private RoadMap _roadMap;
    private Queue<Event> _events;
    private int _time;
    List<TrafficSimObserver> _ob; 

    public TrafficSimulator() {
        this._roadMap = new RoadMap();
        this._events = new PriorityQueue<>();
        this._time = 0;
        this._ob = new ArrayList<TrafficSimObserver>();
    }   
    
    /*GETTERS*/
    
    public int getTime() { return this._time; }

    
    /*SETTERS*/
    
    @Override
	public void addObserver(TrafficSimObserver o) { 
		this._ob.add(o); 
		o.onRegister(_roadMap, _events, _time);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) { this._ob.remove(o); }
    
    public void addEvent(Event e) { 
    	this._events.add(e);
    	
    	for(TrafficSimObserver tso: this._ob)
			tso.onEventAdded(_roadMap, _events, e, _time);
    }
    
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
		
		for(TrafficSimObserver tso: this._ob)
			tso.onAdvance(_roadMap, _events, _time);
	}
	
	public void reset() { 
        this._roadMap = new RoadMap();
        this._events = new PriorityQueue<>();
        this._time = 0;
	
        for(TrafficSimObserver tso: this._ob)
			tso.onReset(_roadMap, _events, _time);
	}
    
	
    /*CHECKERS*/
	
	public boolean isEmpty() { return this._events.isEmpty() && this._roadMap.isEmpty(); }

	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("time", this._time);
		j.put("state", this._roadMap.report());
		return j;
	}

}