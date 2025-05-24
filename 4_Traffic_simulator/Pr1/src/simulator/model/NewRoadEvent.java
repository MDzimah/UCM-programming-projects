package simulator.model;

public abstract class NewRoadEvent extends Event {
	private String id;
	private String srcJunc;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather w;
	

	public NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
  		super(time);
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.w = weather;
	}

	@Override
	void execute(RoadMap map) {
		Junction src = map.getJunction(this.srcJunc);
		Junction dest = map.getJunction(this.destJunc);
		
		map.addRoad(this.initialise(this.id, src, dest, this.maxSpeed, this.co2Limit, this.length, this.w));
	}

	//Through a dynamic linking we achieve to initialize road to its subclasses
	abstract Road initialise(String id, Junction srcJunc, Junction destJunc, int length, int co2Limit, int maxSpeed, Weather weather);
}
