package simulator.view;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import messages.Messages;
import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.*;

@SuppressWarnings("serial")
public class ChangeWeatherDialog extends ChangeEventDialog {
	private static ChangeWeatherDialog instance;
	private Controller ctrl;
	private RoadMap map;
	private ArrayList<JComboBox<String>> cb;
	
	public static ChangeWeatherDialog getInstance(Controller c, RoadMap m) {
		if (instance == null) {
			instance = new ChangeWeatherDialog(c,m);
		}
		return instance;
	}
	
	private ChangeWeatherDialog(Controller c, RoadMap m) {
		this.cb = new ArrayList<JComboBox<String>>();	
		this.ctrl = c;
		this.map = m;
		this.initGui();
	}
	
	
	@Override
	String setName() { return Messages.CHANGE_WEATHER_DIALOG_NAME; }

	@Override
	String setExplanatoryText() { return Messages.CHANGE_WEATHER_DIALOG; }
	
	
	@Override
	JPanel newEventChangerPanel() {
		JPanel p = new JPanel(new FlowLayout());
		
		JLabel l1 = new JLabel("Road:");
		JComboBox<String> roads = new JComboBox<String>(this.ops(map.getRoads()));
		this.cb.add(roads);
		p.add(l1);
		p.add(roads);
		
		JLabel l2 = new JLabel("Weather:");
		JComboBox<String> weathers = new JComboBox<String>(this.ops(new ArrayList<Weather>(EnumSet.allOf(Weather.class))));
		this.cb.add(weathers);
		p.add(l2);
		p.add(weathers);

		return p;
	}
	
	
	@Override
	boolean renewData(Controller c, RoadMap m) {
		if (!this.map.equals(m)) {
			this.cb = new ArrayList<JComboBox<String>>();	
			this.ctrl = c;
			this.map = m;
			return true;
		}
		else return false;
	}
	
	@Override
	void actionPerformed(int time) {
		String road_id_selected = (String) this.cb.get(0).getSelectedItem();
		Weather weather_selected = Weather.valueOf((String) this.cb.get(1).getSelectedItem());
		
		ArrayList<Pair<String, Weather>> info = new ArrayList<Pair<String, Weather>>();
		info.add(new Pair<String,Weather>(road_id_selected, weather_selected));
		Event e = new SetWeatherEvent(this.ctrl.getTime()+time, info);
		this.ctrl.addEvent(e);
	}
}