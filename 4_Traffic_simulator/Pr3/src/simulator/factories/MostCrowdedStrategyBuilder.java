package simulator.factories;
import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{
	
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss", "A new most crowded strategy");
	}

    @Override
    protected void fill_in_data(JSONObject o) {
        o.put("timeslot", "The number of consecutive ticks that the road's light can be green");
    }

    @Override
    protected LightSwitchingStrategy create_instance(JSONObject data) {
    	int timeslot = 1;
    	if (data.has("timeslot")) timeslot = data.getInt("timeslot");
        
        return new MostCrowdedStrategy(timeslot);
    }
}
