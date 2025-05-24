package simulator.view;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.*;
import javax.swing.border.Border;

import messages.Messages;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements TrafficSimObserver{
	
	private Controller ctrl;
	private RoadMap map;
	private final Map<String, JButton> buttons = new LinkedHashMap<>() {{
	    put("open", null);
	    put("co2class", null);
	    put("weather", null);
	    put("run", null);
	    put("stop", null);
	    put("exit", null);
	}};
	private JSpinner ticksInc;
	private JSpinner delayInc;
	private ExecutorService runSimThread = Executors.newSingleThreadExecutor(); //Only one unique thread for running the simulator
	private Future<?> currentTask;
	private static final int MAX_TICKS = 10000;
	private static final int MAX_DELAY = 1000; 
	private static final int NA = 0; //Not Applicable. All the components are going to be added to a JToolBar, which automatically sorts out their sizes
	
	public ControlPanel(Controller ctrl) {
		ctrl.addObserver(this);
		this.ctrl = ctrl;
		this.initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JToolBar jtb = new JToolBar();
		
		for (Map.Entry<String, JButton> p : this.buttons.entrySet()) {
			p.setValue(new JButton());
			p.getValue().setPreferredSize(new Dimension(40, NA));
		}
		this.ticksInc = new JSpinner(new SpinnerNumberModel(10, 0, MAX_TICKS, 1));
		this.delayInc = new JSpinner(new SpinnerNumberModel(10, 0, MAX_DELAY, 1));
		
		this.addButtonsToToolBar(jtb);
		this.add(jtb, BorderLayout.CENTER);
	}
	
	//For initializing the tool bar
	private void addButtonsToToolBar(JToolBar tb) {
		
		//Borders, images and tool tip set
		Border b = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		
		for (Map.Entry<String, JButton> p : this.buttons.entrySet()) {
			//Borders
			p.getValue().setBorder(b);
			
			//Images
			String path = Messages.BUTTON_ICON_ROOT_DIR + p.getKey() + ".png";
			ImageIcon img = this.loadImage(path);
			p.getValue().setIcon(img);
			
			//Tool tips
			p.getValue().setToolTipText(Messages.getToolTipMessage(p.getKey()));
		}
		this.ticksInc.setToolTipText(Messages.getToolTipMessage("ticks"));
		this.delayInc.setToolTipText(Messages.getToolTipMessage("delay"));
		
		
		//Tool bar construction
		tb.setFloatable(true);
		JLabel t = new JLabel("Ticks:");
		this.ticksInc.setMaximumSize(new Dimension(70, 50));
		JLabel d = new JLabel("Delay:");
		this.delayInc.setMaximumSize(new Dimension(70, 50));
		
		tb.add(this.buttons.get("open"));
		tb.addSeparator(new Dimension(15, 35));
		tb.add(this.buttons.get("co2class"));
		tb.add(this.buttons.get("weather"));
		tb.addSeparator(new Dimension(15, 35));
		tb.add(this.buttons.get("run"));
		tb.add(this.buttons.get("stop"));
		tb.addSeparator(new Dimension(10, NA));
		tb.add(t);
		tb.addSeparator(new Dimension(5, NA));
		tb.add(this.ticksInc);
		tb.addSeparator(new Dimension(10, NA));
		tb.add(d);
		tb.addSeparator(new Dimension(5, NA));
		tb.add(this.delayInc);
		tb.add(Box.createHorizontalGlue());
		tb.addSeparator(new Dimension(15, 35));
		tb.add(this.buttons.get("exit"));
		tb.addSeparator(new Dimension(3, NA));
		
		
		//Setting up the listeners for the sources (the buttons)
		this.buttons.get("open").addActionListener(new GestorOpen(this.ctrl));
		this.buttons.get("co2class").addActionListener(e -> {
			if(!ControlPanel.this.map.isEmpty()) {
				ChangeCO2ClassDialog co2d = ChangeCO2ClassDialog.getInstance(ControlPanel.this.ctrl, ControlPanel.this.map);
				co2d.renewEventChangerPanel(ControlPanel.this.ctrl, ControlPanel.this.map);
				co2d.setVisible(true);
			}
			else JOptionPane.showMessageDialog(null, Messages.RUN_ERROR_DIALOG, 
					Messages.RUN_ERROR_DIALOG_NAME,JOptionPane.ERROR_MESSAGE);
		});
		this.buttons.get("weather").addActionListener(e -> {
			if(!ControlPanel.this.map.isEmpty()) {
				ChangeWeatherDialog wd = ChangeWeatherDialog.getInstance(ControlPanel.this.ctrl, ControlPanel.this.map);
				wd.renewEventChangerPanel(ControlPanel.this.ctrl, ControlPanel.this.map);
				wd.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, Messages.RUN_ERROR_DIALOG, 
						Messages.RUN_ERROR_DIALOG_NAME, JOptionPane.ERROR_MESSAGE);
			}
		});
		this.buttons.get("run").addActionListener(e -> {
			try {	
				//So that the spinner updates its value even when it isn't committed within the spinner's context
				ControlPanel.this.ticksInc.commitEdit(); 
				ControlPanel.this.delayInc.commitEdit();
				ControlPanel.this.run_sim((Integer)ControlPanel.this.ticksInc.getValue(), 
						(Integer)ControlPanel.this.delayInc.getValue());
		    }
			catch (Exception ex) { 
				ControlPanel.this.ticksInc.updateUI();
				ControlPanel.this.delayInc.updateUI();
			}	
		});
		this.buttons.get("stop").addActionListener(e -> {
			if (this.currentTask != null) {
				this.currentTask.cancel(true);
			}
		});
		this.buttons.get("exit").addActionListener(e -> {
			int choice = JOptionPane.showConfirmDialog(null, 
					Messages.EXIT_DIALOG, 
					Messages.EXIT_DIALOG_NAME, 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION) 
				System.exit(0);
			else ControlPanel.this.buttons.get("exit").setEnabled(true);
		});
	}
	
	
	/*UI/DATA UPDATE METHODS*/
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
	}

	
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			this.map = map;
			this.repaint();
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
	
	private void run_sim(int n, long delay) {
		for (Map.Entry<String, JButton> p : this.buttons.entrySet()) {
			if (!p.getKey().equals("stop")) p.getValue().setEnabled(false);
		}	
		
		this.currentTask = runSimThread.submit(()->{
			int ticks = n;
			while (ticks > 0 && !Thread.currentThread().isInterrupted()) {
				try {
					ctrl.run(1); //Throws exception if the event queue and the road map are both empty
				} catch (Exception e) {
					SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, Messages.RUN_ERROR_DIALOG, 
							Messages.RUN_ERROR_DIALOG_NAME,JOptionPane.ERROR_MESSAGE);
					});
					Thread.currentThread().interrupt();
				}
				try {
					Thread.sleep(delay);
				}
				catch(InterruptedException e) {
					Thread.currentThread().interrupt(); //Restore flag of interrupt to true
				}
				--ticks;
			}
			
			SwingUtilities.invokeLater(() -> {
				for (Map.Entry<String, JButton> p : buttons.entrySet()) {
					p.getValue().setEnabled(true);
				}
			});
		});
	}
	
	private ImageIcon loadImage(String path) { return new ImageIcon(path); }
}
