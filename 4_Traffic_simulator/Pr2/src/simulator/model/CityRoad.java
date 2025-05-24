package simulator.model;

public class CityRoad extends Road{
	
	CityRoad(String id, Junction bI, Junction eI, int maxSpeed, int maxCont, int length, Weather w) {
		super(id, bI, eI, maxSpeed, maxCont, length, w);
	}

	/*SETTERS*/
	
	@Override
	void reduceTotalContamination() {
		int x;
		switch (this.getWeather()) {
			case WINDY, STORM: x = 10; break;
			default: x = 2; break;
		}
		
		if (this.getTotalCO2()- x < 0) this.setTotalCO2(0);
		else this.setTotalCO2(this.getTotalCO2()-x);
	}

	@Override
	void updateSpeedLimit() { this.setActualMaxSpeed(this.getMaxSpeed()); }

	
	/*GETTERS*/
	
	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int s = ((11-v.getContClass())*this.getMaxSpeed())/11;
		return Integer.min(s, v.getMaxSpeed());
	}
}
