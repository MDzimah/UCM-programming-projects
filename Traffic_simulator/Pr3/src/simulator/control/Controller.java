package simulator.control;


import org.json.*;
import java.io.*;
import messages.Messages;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
    private TrafficSimulator _sim;
    private Factory<Event> _eventsFactory;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
        if (sim != null && eventsFactory != null) {
            _sim = sim;
            _eventsFactory = eventsFactory;
        }
        else throw new IllegalArgumentException(Messages.INVALID_CLASS_ARGUMENTS.formatted("controller constructor"));
    }
    
    
    /*GETTERS*/
   
    public int getTime() { return this._sim.getTime(); }
    
    
    /*SETTERS*/
    
    public void addObserver(TrafficSimObserver o) { this._sim.addObserver(o); }
    public void removeObserver(TrafficSimObserver o) { this._sim.removeObserver(o); }
    
    public void addEvent(Event e) { this._sim.addEvent(e); }
    
    public void loadEvents(InputStream in) {
        JSONObject jo = new JSONObject(new JSONTokener(in));
        if (jo.has("events")) {
            JSONArray ja = jo.getJSONArray("events");
        	for(int i = 0; i < ja.length(); ++i) {
        		this._sim.addEvent(this._eventsFactory.create_instance(ja.getJSONObject(i)));
        	}
        }
        else throw new IllegalArgumentException(Messages.INVALID_OBJECT.formatted("JSONObject format"));
    }
    
    public void run(int n) throws Exception { 
    	if (this._sim.isEmpty()) throw new Exception();
    	else { for (int i = 0; i < n; ++i)
    		this._sim.advance();
    	}
    }  
    
    public boolean isSimulatorEmpty() { return this._sim.isEmpty(); }
     
    public void run(int n, OutputStream out) {
    	PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("  \"states\": [");
        
    	for (int i = 0; i < n-1; ++i) {
    		this._sim.advance();
    		p.println(this._sim.report() + ",");
    	}
    	
    	if (n > 0) {
    		this._sim.advance();
			p.print(_sim.report());
    	}
    	p.println(System.lineSeparator() + "]");
    	p.println("}");
    }
    
    public void reset() { this._sim.reset(); }
    
    
}
