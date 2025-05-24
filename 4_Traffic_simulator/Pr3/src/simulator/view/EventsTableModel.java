package simulator.view;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private ArrayList<Event> events;
	private final int numColumns = 2;
	private final String[] cols = {"Time", "Desc."};
	
	public EventsTableModel(Controller ctrl) {
		ctrl.addObserver(this);
	}
	
	
	/*TABLE CONTENTS*/
	
	@Override
	public int getRowCount() { 
		if (this.events != null) return this.events.size(); 
		else return 0;
	}

	@Override
	public int getColumnCount() { return this.numColumns; }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Event e = this.events.get(rowIndex);
		if(columnIndex == 0) return Integer.valueOf(e.getTime());
		else return e.toString();
	}
	
	@Override
	public String getColumnName(int column) { return this.cols[column]; }

	
	/*UI/DATA UPDATE METHODS*/
	
	public void update(Collection<Event> events) {
		SwingUtilities.invokeLater(() -> {
			this.events = new ArrayList<>(events);
			this.fireTableStructureChanged();
		});
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(events);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(events);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(events);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(events);
	}
}
