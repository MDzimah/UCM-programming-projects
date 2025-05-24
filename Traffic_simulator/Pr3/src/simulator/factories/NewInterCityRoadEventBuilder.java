package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event>{
	public NewInterCityRoadEventBuilder() {
        super("new_inter_city_road", "A new inter city road");
    }

	@Override
	protected void fill_in_data(JSONObject o) {
	    o.put("time", "The time at which the event is executed"); 
	    o.put("id", "The inter city road's id");
	    o.put("src", "The inter city road's beginning intersection");
	    o.put("dest", "The inter city road's ending intersection");
	    o.put("length", "The inter city road's length");
	    o.put("co2limit", "The inter city road's co2 limit");
	    o.put("maxspeed", "The inter city road's max speed");
	    o.put("weather", "The inter city road's weather");
	}
	
	@Override
	protected Event create_instance(JSONObject data) {
	    int time = data.getInt("time");
	    String id = data.getString("id");
	    int maxSpeed = data.getInt("maxspeed");
	    int co2limit = data.getInt("co2limit");
	    int length = data.getInt("length");
	    String src = data.getString("src");
	    String dest = data.getString("dest");
	    Weather weather = Weather.valueOf(data.getString("weather").toUpperCase());
	    
	    return new NewInterCityRoadEvent(time, id, src, dest, length, co2limit, maxSpeed, weather);
	}
}
