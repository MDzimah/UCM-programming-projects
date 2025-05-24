package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event> {
	 
	public NewCityRoadEventBuilder() {
	        super("new_city_road", "A new city road");
	    }

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("time", "The time at which the event is executed"); 
        o.put("id", "The city road's id");
        o.put("src", "The city road's beginning intersection");
        o.put("dest", "The city road's ending intersection");
        o.put("length", "The city road's length");
        o.put("co2limit", "The city road's co2 limit");
        o.put("maxspeed", "The city road's max speed");
        o.put("weather", "The city road's weather");
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
        
        return new NewCityRoadEvent(time, id, src, dest, length, co2limit, maxSpeed, weather);
    }
}
