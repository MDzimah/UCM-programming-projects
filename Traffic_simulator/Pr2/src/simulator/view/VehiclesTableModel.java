package simulator.view;

import java.util.Collection;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	private RoadMap map;
	private final int numColumns = 8;
	private final String[] cols = {"Id", "Location", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};
	
	public VehiclesTableModel(Controller ctrl) {
		ctrl.addObserver(this);
	}
	
	
	/*TABLE CONTENTS*/
	
	@Override
	public int getRowCount() { 
		if (this.map != null) return this.map.getVehicles().size(); 
		else return 0;
	}

	@Override
	public int getColumnCount() { return this.numColumns; }
	
	@Override
	public String getColumnName(int column) { return this.cols[column]; }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vehicle v = this.map.getVehicles().get(rowIndex);
		switch(columnIndex) {
		case 0: return v.getId();
		case 1: return status(v);
		case 2: return v.getItinerary().toString();
		case 3: return v.getContClass();
		case 4: return v.getMaxSpeed();
		case 5: return v.getSpeed();
		case 6: return v.getTotalCO2();
		default: return v.getTotalDistance();
		}
	}
	
	
	/*UI/DATA UPDATE METHODS*/
	
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			this.map = map;
			this.fireTableStructureChanged();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}
	
	
	/*PRIVATE*/
	
	private String status(Vehicle v) {
		switch(v.getStatus()) {
		case PENDING: return "Pending";
		case TRAVELING: return v.getId() + ':' + v.getLocation();
		case WAITING: return "Waiting:" + v.getRoad().getDest(); 
		default: return "Arrived";
		}
	}
}
