package simulator.model;

import java.util.*;

import messages.Messages;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
    List<Pair<String, Weather>> ws;

    public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
        super(time);
        if(ws != null)  this.ws = ws;
        else throw new IllegalArgumentException(Messages.INVALID_CLASS_ARGUMENTS.formatted("SetWeatherEvent"));
    }

	@Override
	void execute(RoadMap map) {
		for(int i = 0; i < ws.size(); ++i) {
            Road r = map.getRoad(ws.get(i).getFirst());
            if (r != null) r.setWeather(this.ws.get(i).getSecond());
            else throw new IllegalArgumentException (Messages.INVALID_OBJECT.formatted("road"));
        }
	}
}
