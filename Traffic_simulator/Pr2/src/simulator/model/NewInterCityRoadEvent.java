package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {
	public NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
  		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	Road initialise(String id, Junction srcJunc, Junction destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		return new InterCityRoad(id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}
}
