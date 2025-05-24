package simulator.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;

import javax.swing.*;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements TrafficSimObserver {
	private Event event;
	private int time;
	private JLabel timeLabel;	
	private JLabel eventLabel;
	
	public StatusBar(Controller ctrl) {
		ctrl.addObserver(this);
		this.initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		this.timeLabel = new JLabel();
		this.timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.eventLabel = new JLabel("Welcome!");
		this.eventLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setMaximumSize(new Dimension(15, 10));
		sep.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		this.add(Box.createHorizontalStrut(10));
		this.add(this.timeLabel);
		this.add(Box.createHorizontalStrut(150));
		this.add(sep);
		this.add(this.eventLabel);
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	}
	
	
	/*UI/DATA UPDATE METHODS*/
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		if (this.event != null) { 
			this.eventLabel.setText("Event added (" + this.event.toString() + ')'); 
		}
		else if (!this.eventLabel.getText().equals("Welcome!")) this.eventLabel.setText("");
		
		this.timeLabel.setText("Time: " + this.time);
	}
	
	public void update(Event e, int time) {
		SwingUtilities.invokeLater(() -> {
			this.time = time;
			this.event = e;
			this.repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(null, time);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(e, time);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(null, time);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(null, time);
	}
}
