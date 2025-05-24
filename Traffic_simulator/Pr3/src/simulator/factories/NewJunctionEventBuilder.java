package simulator.factories;

import org.json.JSONObject;
import simulator.model.*;


public class NewJunctionEventBuilder extends Builder<Event>{
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction", "A new junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
    protected void fill_in_data(JSONObject o) {
		o.put("time", "The time at which the event is executed");
	    o.put("id", "The junction's id");
	    o.put("coor", "The junction's coordinates");
	    o.put("ls_strategy", "The junction's light switching strategy");
	    o.put("dq_strategy", "The junction's dequeuing strategy");
	}

    @Override
    protected Event create_instance(JSONObject data) {
       int time = data.getInt("time");
       String id = data.getString("id");
       int x = data.getJSONArray("coor").getInt(0);
       int y = data.getJSONArray("coor").getInt(1);
       /*For objects there is no obvious way of retrieving them from data. Thus
       we take advantage of lss factory and dqs factory to create the instances of the 
       objects*/ 
       LightSwitchingStrategy lss = this.lssFactory.create_instance(data.getJSONObject("ls_strategy"));
       DequeuingStrategy dqs = this.dqsFactory.create_instance(data.getJSONObject("dq_strategy"));
       
        return new NewJunctionEvent(time, id, lss, dqs, x, y);
    }
}
