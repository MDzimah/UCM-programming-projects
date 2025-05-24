package simulator.model;

public class NewJunctionEvent extends Event {
	
	private Junction j;
	
    public NewJunctionEvent(int time, String id, LightSwitchingStrategy lss, DequeuingStrategy dqs, int x, int y) {
        super(time);
      	this.j = new Junction(id, lss, dqs, x, y);
    }

	@Override
	void execute(RoadMap map) {	map.addJunction(j); }
}
