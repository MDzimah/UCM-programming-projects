package simulator.model;

import java.util.Collection;

public interface TrafficSimObserver {
	void onAdvance(RoadMap map, Collection<Event> events, int time);
	void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time);
	void onReset(RoadMap map, Collection<Event> events, int time);
	void onRegister(RoadMap map, Collection<Event> events, int time);
}

