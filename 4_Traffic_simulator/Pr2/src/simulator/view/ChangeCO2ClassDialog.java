package simulator.view;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import messages.Messages;
import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.*;

@SuppressWarnings("serial")
public class ChangeCO2ClassDialog extends ChangeEventDialog {
	private static ChangeCO2ClassDialog instance;
	private Controller ctrl;
	private RoadMap map;
	private ArrayList<JComboBox<String>> cb;
	
	public static ChangeCO2ClassDialog getInstance(Controller c, RoadMap m) {
		if (instance == null) {
			instance = new ChangeCO2ClassDialog(c,m);
		}
		return instance;
	}
	
	private ChangeCO2ClassDialog(Controller ctrl, RoadMap map) {
		this.cb = new ArrayList<JComboBox<String>>();	
		this.ctrl = ctrl;
		this.map = map;
		this.initGui();
	}
	
	
	@Override
	String setName() { return Messages.CHANGE_CO2_DIALOG_NAME; }

	@Override
	String setExplanatoryText() { return Messages.CHANGE_CO2_DIALOG; }

	
	@Override
	JPanel newEventChangerPanel() {
		JPanel p = new JPanel(new FlowLayout());
		
		JLabel l1 = new JLabel("Vehicle:");
		//Default behaviour: JComboBox shows on its selected area the first element put in it
		JComboBox<String> vehicles = new JComboBox<String>((this.ops(this.map.getVehicles())));
		cb.add(vehicles);
		p.add(l1);
		p.add(vehicles);
		
		JLabel l2 = new JLabel("CO2 Class:");
		String[] co2values= {"0", "1", "2", "3", "4", "5" ,"6", "7", "8", "9", "10"};
		JComboBox<String> co2class = new JComboBox<String>(co2values);
		cb.add(co2class);
		p.add(l2);
		p.add(co2class);

		return p;
	}
	
	
	@Override
	boolean hasToRenewData(Controller c, RoadMap m) {
		if (!this.map.equals(m)) {
			this.cb = new ArrayList<JComboBox<String>>();	
			/*Works. Maybe this is cleaner and more UI friendly:
			comboBox.removeAllItems();
			for (Data d : data) {
			    this.comboBox.addItem(d);
			}
			*/
			this.ctrl = c;
			this.map = m;
			return true;
		}
		else return false;
	}
	
	@Override
	void actionPerformed(int time) {
		String vehicle_id_selected = (String) this.cb.get(0).getSelectedItem();
		if (this.map.getVehicle(vehicle_id_selected).getStatus() != VehicleStatus.ARRIVED) {
			Integer contClass_selected = Integer.valueOf((String) this.cb.get(1).getSelectedItem());
			
			ArrayList<Pair<String, Integer>> info = new ArrayList<Pair<String, Integer>>();
			info.add(new Pair<String,Integer>(vehicle_id_selected, contClass_selected));
			Event e = new SetContClassEvent(this.ctrl.getTime()+time, info);
			this.ctrl.addEvent(e);
		}
		else {
			 JOptionPane.showMessageDialog(null, 
             		Messages.VEHICLE_ARRIVED_DIALOG,
             		Messages.VEHICLE_ARRIVED_DIALOG_NAME, 
             		JOptionPane.ERROR_MESSAGE);
		}
	}
}