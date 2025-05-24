package simulator.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;



@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    private Controller _ctrl;

    public MainWindow(Controller ctrl) {
        super("Traffic Simulator");
        _ctrl = ctrl;
        initGUI();
    }

    private void initGUI() {
        //Main panel setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);

        //ControlPanel and StatusBar
        mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
        mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);

        //Panel for tables and maps
        JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(viewsPanel, BorderLayout.CENTER);

        //Tables panel
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(tablesPanel);

        //Maps panel
        JPanel mapsPanel = new JPanel();
        mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(mapsPanel);

        //Tables added to tablePanel
        tablesPanel.add(createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events"));
        tablesPanel.add(createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles"));
        tablesPanel.add(createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads"));
        tablesPanel.add(createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions"));
        
        /*The tables could have been done inside the MainWindow directly as anonymous classes and doing sth like this:
	    	DefaultTableModel model = new DefaultTableModel(new String[]{ "Id","Contamination class", ... }, 0);
	    	for (Road r : map.getRoads()) {
	    	    model.addRow(new Object[]{ r.getId(), r.getContClass(), ... });
	    	}
	    	JTable table = new JTable(model); --> this would be the "this" object
	    	JScrollPane scroll = new JScrollPane(table);
	    	tablesPanel.add(scroll);
    	*/

        //Set the same preferred size for both map components
        JPanel mapComponent = createViewPanel(new MapComponent(_ctrl), "Map");
        //Not functional, simply to tell the JFrame to give this component the same size as the MapByRoadComponent
        mapComponent.setPreferredSize(new Dimension(0, 0)); 

        JPanel mapByRoadComponent = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
        mapByRoadComponent.setPreferredSize(new Dimension(0, 0));

        //Add the map components to mapsPanel
        mapsPanel.add(mapComponent);
        mapsPanel.add(mapByRoadComponent);
       
        //Maximize the window
        this.setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int)(screenSize.width/2), (int)(screenSize.height/1.5)); //For when the screen is dragged or full screen mode is disabled
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //Screen initially extends to the whole screen size
        this.setVisible(true);
    }

    private JPanel createViewPanel(JComponent c, String title) {
    	JPanel p = new JPanel(new BorderLayout());
		TitledBorder b = new TitledBorder(title); 
		b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p.setBorder(b);
		p.add(new JScrollPane(c));
		return p;
    }
}
