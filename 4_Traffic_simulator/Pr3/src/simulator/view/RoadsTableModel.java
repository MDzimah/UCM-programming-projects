package simulator.view;

import java.util.Collection;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Road;

@SuppressWarnings("serial")
public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	RoadMap map;
	private final int numColumns = 7;
	private final String[] cols = {"Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	public RoadsTableModel(Controller ctrl) {
		ctrl.addObserver(this);
	}
	
	
	/*TABLE CONTENTS*/
	
	@Override
	public int getRowCount() { 
		if (this.map != null) return this.map.getRoads().size(); 
		else return 0;
	}

	@Override
	public int getColumnCount() { return this.numColumns; }
	
	@Override
	public String getColumnName(int column) { return this.cols[column]; }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Road r = this.map.getRoads().get(rowIndex);
		switch(columnIndex) {
		case 0: return r.getId();
		case 1: return r.getLength();
		case 2: return r.getWeather().toString();
		case 3: return r.getMaxSpeed();
		case 4: return r.getSpeedLimit();
		case 5: return r.getTotalCO2();
		default: return r.getContLimit();
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
}
