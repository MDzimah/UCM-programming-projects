package simulator.view;

import java.util.Collection;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	RoadMap map;
	private final int numColumns = 3;
	private final String[] cols = {"Id", "Green", "Queues"};
	
	public JunctionsTableModel(Controller ctrl) {
		ctrl.addObserver(this);
	}
	
	
	/*TABLE CONTENTS*/
	
	@Override
	public int getRowCount() { 
		if (this.map != null) return this.map.getJunctions().size(); 
		else return 0;
	}

	@Override
	public int getColumnCount() { return this.numColumns; }
	
	@Override
	public String getColumnName(int column) { return this.cols[column]; }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Junction j = this.map.getJunctions().get(rowIndex);
		switch(columnIndex) {
		case 0: return j.getId();
		case 1: int gli = j.getGreenLightIndex(); return gli == -1 ? "NONE" : j.getInRoads().get(gli).getId();
		default: return junctionQueues(j);
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
	
	String junctionQueues(Junction j) {
		StringBuilder s = new StringBuilder();
		for (Road r : j.getInRoads()) {
	        s.append(r.getId()).append(": ["); 
	        
	        boolean first = true;
	        for (Vehicle v : j.getInRoad_Queue().get(r)) {
	            if (!first) {
	                s.append(", ");
	            }
	            s.append(v.getId());
	            first = false;
	        }
	        
	        s.append("] ");
	    }
	    
	    return s.toString().trim();
	}
}
