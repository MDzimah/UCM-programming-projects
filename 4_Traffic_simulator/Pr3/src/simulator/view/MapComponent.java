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

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapComponent extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;

	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;

	private Image _car;

	MapComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this._car = loadImage("car_front.png");
	}
	
	
	/*UI/DATA UPDATE METHODS*/

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			this.updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);
		drawJunctions(g);
	}

	private void drawRoads(Graphics g) {
		for (Road r : _map.getRoads()) {

			//Road goes from (x1,y1) to (x2,y2)
			int x1 = r.getSrc().getX();
			int y1 = r.getSrc().getY();
			int x2 = r.getDest().getX();
			int y2 = r.getDest().getY();

			//Color for the arrow depends on the traffic light of the road
			Color arrowColor = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				arrowColor = _GREEN_LIGHT_COLOR;
			}

			//Color for the road depends on the total contamination, the darker the more contaminated
			int roadColorValue = 200
					- (int) (200.0 * Math.min(1.0, (double) r.getTotalCO2() / (1.0 + (double) r.getContLimit())));
			Color roadColor = new Color(roadColorValue, roadColorValue, roadColorValue);

			//Draws a line with an arrow from (x1,y1) to (x2,y2) 15px length and 5 px width
			drawLineWithArrow(g, x1, y1, x2, y2, 15, 5, roadColor, arrowColor);
		}

	}

	private void drawVehicles(Graphics g) {
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				Road r = v.getRoad();
	
				/*The calculation below computes the coordinate (vX,vY) of the vehicle on the
				corresponding road. It is calculated relative to the length of the road and
				the location on the vehicle*/
				int x1 = r.getSrc().getX();
				int y1 = r.getSrc().getY();
				int x2 = r.getDest().getX();
				int y2 = r.getDest().getY();

				double f = ((float)v.getLocation()) / r.getLength();
				int vX = (int)(x1 + (x2-x1)*f); 
				int vY = (int)(y1 + (y2-y1)*f);

				//Vehicle's label color depends on its contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				//Draws an image of a car (with circle as background) and its identifier
				g.fillOval(vX - 1, vY - 6, 14, 14);
				g.drawImage(_car, vX, vY - 6, 12, 12, this);
				g.drawString(v.getId(), vX, vY - 6);
			}
		}
	}

	private void drawJunctions(Graphics g) {
		for (Junction j : _map.getJunctions()) {

			//(x,y) are the coordinates of the junction
			int x = j.getX();
			int y = j.getY();

			//Draws a circle with center at (x,y) with radius _JRADIUS
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

			//Draws the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(j.getId(), x, y);
		}
	}

	//Called when drawing outside of the preset size. It adds a scroll bar to the the panel
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}

	//Draws a line from (x1,y1) to (x2,y2) with an arrow
	//The arrow is of height h and width w
	//The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(//
			Graphics g, //
			int x1, int y1, //
			int x2, int y2, //
			int w, int h, //
			Color lineColor, Color arrowColor) {

		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
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
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
}
