package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.*;

@SuppressWarnings("serial")
public class MapByRoadComponent extends JComponent implements TrafficSimObserver{

	private RoadMap _map;
	
	private static final int _JRADIUS = 10;
	
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	private final ImageIcon _car = new ImageIcon("resources/icons/car.png");
	private final Map<Weather, ImageIcon> weatherImages = new HashMap<>() {{
	    put(Weather.CLOUDY, new ImageIcon("resources/icons/cloud.png"));
	    put(Weather.RAINY, new ImageIcon("resources/icons/rain.png"));
	    put(Weather.WINDY, new ImageIcon("resources/icons/wind.png"));
	    put(Weather.STORM, new ImageIcon("resources/icons/storm.png"));
	    put(Weather.SUNNY, new ImageIcon("resources/icons/sun.png"));
	}};
	
	MapByRoadComponent(Controller ctrl) {
		ctrl.addObserver(this);
		setPreferredSize(new Dimension(300,200));
	}
	
	
	/*UI/DATA UPDATE METHODS*/
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		//Clear background
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map by road yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			this.updatePrefferedSize();
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {

			//The road goes from (x1,y1) to (x2,y2)
			int x1 = 50;
			int x2 = getWidth()-100;
			int y = (i+1)*50;
			
			//Draws the line of the i-th road
			g.setColor(Color.BLACK);
			g.drawLine(x1, y, x2, y);
			g.drawString(r.getId(), x1-30, y);
			
			//Draws junction circles at start and end of i-th road:
			//Start
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().getId(), x1, y - 8);
			
			//End
			Color junctionColour = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				junctionColour = _GREEN_LIGHT_COLOR;
			}			
			g.setColor(junctionColour);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getDest().getId(), x2, y - 8);
						
			//Draws the vehicles on the i-th road
			this.drawVehicles(g, r.getVehicles(), x1,x2,y);
			
			//Draws weather and contamination
			g.drawImage(this.weatherImages.get(r.getWeather()).getImage(), x2+10, y-16, 32, 32, this);
			
			int C = (int) Math.floor(Math.min((double) r.getTotalCO2()/(1.0 + (double) r.getContLimit()),1.0) / 0.19);
			g.drawImage(this.loadImage("cont_" + C + ".png"), x2+46, y-16, 32, 32, this);
			++i;
		}

	}

	private void drawVehicles(Graphics g, List<Vehicle> v_L, int x1, int x2, int y) {
		for (Vehicle v : v_L) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {
				Road r = v.getRoad();
	
				/*The calculation below computes the coordinate (vX,vY) of the vehicle on the
				corresponding road. It is calculated relative to the length of the road and
				the location on the vehicle*/
				int x = (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength()) + x1);
				
				//Vehicle's label color depends on its contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				//Draw an image of a car and its identifier
				g.drawImage(this._car.getImage(), x, y-11, 16, 16, this);
				g.drawString(v.getId(), x, y-13);
			}
		}
	}
	
	
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
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
	
	private void updatePrefferedSize() {
		int maxH = this._map.getRoads().size()*50 + 20;
		if (maxH > getHeight()) {
			setPreferredSize(new Dimension(getWidth(), maxH));
			setSize(new Dimension(getWidth(), maxH));
		}
	}
	
	//Loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
}
