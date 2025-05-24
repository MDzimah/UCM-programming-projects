package simulator.model;

public class InterCityRoad extends Road{
	
	InterCityRoad(String id, Junction bI, Junction eI, int maxSpeed, int maxCont, int length, Weather w) {
		super(id, bI, eI, maxSpeed, maxCont, length, w);
	}

	
	/*SETTERS*/
	
	@Override
	void reduceTotalContamination() {
		int x;
		switch (this.getWeather()) {
			case SUNNY: x = 2; break; 
			case CLOUDY: x = 3; break; 
			case RAINY: x = 10; break; 
			case WINDY: x = 15; break;
			default: x = 20; break;
		}
		
		this.setTotalCO2(((100-x)*this.getTotalCO2())/100);
	}

	@Override
	void updateSpeedLimit() {
		if(this.getTotalCO2() > this.getContLimit()) {
			this.setActualMaxSpeed(this.getMaxSpeed()/2);
		}
		else this.setActualMaxSpeed(this.getMaxSpeed());
	}

	
	/*GETTERS*/
	
	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int s = v.getMaxSpeed();
		int speedLimit = this.getSpeedLimit();
		
		if(this.getWeather().equals(Weather.STORM)) 
			speedLimit = (speedLimit*8)/10;

		return Integer.min(speedLimit, s);	
	}
}
