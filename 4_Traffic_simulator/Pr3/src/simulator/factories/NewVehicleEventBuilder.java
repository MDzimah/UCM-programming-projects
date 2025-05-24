package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import simulator.model.*;

public class NewVehicleEventBuilder extends Builder<Event> {

    public NewVehicleEventBuilder() {
        super("new_vehicle", "A new vehicle");
    }

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("time", "The time at which the event is executed");
        o.put("maxspeed", "The vehicle's max speed");
        o.put("id", "The vehicle's id");
        o.put("class", "The vehicle's contamination class");
        o.put("itinerary", "The vehicle's itinerary");
    }

    @Override
    protected Event create_instance(JSONObject data) {
        int time = data.getInt("time");
        String id = data.getString("id");
        int maxSpeed = data.getInt("maxspeed");
        int contClass = data.getInt("class");
        
        List<String> itinerary = new ArrayList<String>();
        
        
        JSONArray ja = data.getJSONArray("itinerary");
		for (int i = 0; i < ja.length(); ++i) {
			itinerary.add(ja.getString(i));
		}
        
		/*NewVehicleEvent can access the RoadMap through its execute method. 
		In it, it converts the String itinerary into a Junction itinerary
		which can be used by the vehicles*/
        return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
    }
}
