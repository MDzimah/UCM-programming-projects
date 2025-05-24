package simulator.factories;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;
import simulator.misc.Pair;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather", "A new weather condition");
	}
	
	@Override
    protected void fill_in_data(JSONObject o) {
		o.put("time", "The time at which the event is executed");
    }
	
	
	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		List<Pair<String,Weather>> ws = new ArrayList<Pair<String, Weather>>();
		
		JSONArray ja = data.getJSONArray("info");
		for (int i = 0; i < ja.length(); ++i) {
			ws.add(new Pair<String,Weather>(ja.getJSONObject(i).getString("road"), Weather.valueOf(ja.getJSONObject(i).getString("weather").toUpperCase())));
		}
		  
		return new SetWeatherEvent(time, ws);
	}

}
