package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class", "A new contamination class");
	}
	
	@Override
    protected void fill_in_data(JSONObject o) {
		o.put("time", "The time at which the event is executed");
    }
	
	
	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		List<Pair<String,Integer>> cc = new ArrayList<Pair<String, Integer>>();
		
		JSONArray ja = data.getJSONArray("info");
		for (int i = 0; i < ja.length(); ++i) {
			cc.add(new Pair<String,Integer>(ja.getJSONObject(i).getString("vehicle"), ja.getJSONObject(i).getInt("class")));
		}
		  
		return new SetContClassEvent(time, cc);
	}

}
