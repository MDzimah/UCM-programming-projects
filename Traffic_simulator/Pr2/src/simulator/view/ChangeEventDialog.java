package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.RoadMap;

/*This is the superclass of all change event dialogs (co2 and weather) which are both
singleton classes. When the button associated to the dialogs is triggered, both dialogs
renew their data before showing themselves as the user could have loaded in new data to the
traffic simulator. After this, the event changer panel is also actualized. 

This implementation ensures that the immutable aspects of the change event dialogs
remain without redoing and that there is little to no code repetition.*/
@SuppressWarnings("serial")
public abstract class ChangeEventDialog extends JDialog {
	private static final int MAX_TICKS = 10000;
	private JSpinner ticks;
	private JPanel eventChangerPanel;

	abstract String setName();		
	abstract String setExplanatoryText();
	
	void initGui() {
		this.setTitle(this.setName());
		this.setLayout(new BorderLayout()); //this.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); could be cleaner layout
		this.setModal(true); //Any interaction with other windows isn't possible while this one is visible
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width / 2, screenSize.height / 5);
		this.setLocationRelativeTo(null);
		
		
		//Explanatory text of the dialog
		JPanel textPanel = new JPanel(new FlowLayout());
		JLabel text = new JLabel(this.setExplanatoryText());
		textPanel.add(text);
		this.add(textPanel, BorderLayout.NORTH);
		
		
		//Event changer panel
		this.eventChangerPanel = this.newEventChangerPanel();
		JLabel incTicksLabel = new JLabel("Ticks:");
		this.ticks = new JSpinner(new SpinnerNumberModel(1, 1, MAX_TICKS, 1));
		this.eventChangerPanel.add(incTicksLabel);
		this.eventChangerPanel.add(this.ticks);
		this.add(eventChangerPanel, BorderLayout.CENTER);
		
		
		//Ok and cancel buttons panel
		JPanel responsePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		//Also could have added padding doing responsePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		//Or doing this.add(Box.createVerticalStrut(10));
		JButton cancel = new JButton("Cancel");
		JButton ok = new JButton("OK");
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	
					 ChangeEventDialog.this.ticks.commitEdit(); 
			     } 
				 catch (Exception ex) { 
					 ChangeEventDialog.this.ticks.updateUI(); 
				 }
				 
				ChangeEventDialog.this.actionPerformed((Integer)ChangeEventDialog.this.ticks.getValue());
				ChangeEventDialog.this.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeEventDialog.this.dispose();
			}
		});
		responsePanel.add(ok);
		responsePanel.add(cancel);
		this.add(responsePanel, BorderLayout.SOUTH);
		
		/*
		For better scalability could have used CardLayout
	
			CardLayout cardLayout = new CardLayout();
			JPanel multiplePanels = new JPanel(cardLayout);
			...
			... (say I make 3 JPanels with jp0, 1, 2 respectively here)
			...
			multiplePanels.add(jp0, "card0");
			multiplePanels.add(jp0, "card1");
			multiplePanels.add(jp0, "card2");
			//Default behaviour: cardLayout shows on multiplePanels first card put (it will show card0 initially)
			...
			... (next, previous buttons with currentCardIndex to keep track) 
			...
			prev.addActionListener(e -> {
				if (currentCardIndex > 0) {
					currentCardIndex--;
					cardLayout.show(multiplePanels, "card" + currentCardIndex);
				}
			});
			
			next.addActionListener(e -> {
				if (currentCardIndex < 2) {
					currentCardIndex++;
					cardLayout.show(multiplePanels, "card" + currentCardIndex);
				}
		 */
	}
	
	
	/*EVENT CHANGER PANEL*/
	
	abstract JPanel newEventChangerPanel();
	
	void renewEventChangerPanel(Controller c, RoadMap m) {
		if (this.hasToRenewData(c, m)) {
			this.remove(this.eventChangerPanel);
			
			//Redoing the eventChangerPanel
			this.eventChangerPanel = this.newEventChangerPanel();
			this.eventChangerPanel.add(new JLabel("Ticks:"));
			this.eventChangerPanel.add(this.ticks);
			
			this.add(this.eventChangerPanel, BorderLayout.CENTER);
			this.revalidate();
			this.repaint();
		}
	}
	
	
	/*DIALOG DATA/ACTION*/
	
	abstract boolean hasToRenewData(Controller c, RoadMap m);
	
	abstract void actionPerformed(int time);
	
	//For the child classes to convert their combo boxes selections to a string of options if needed	
	<T> String[] ops(List<T> list) {
		ArrayList<String> s = new ArrayList<String>();
		for (T t : list) { s.add(t.toString()); }
		return s.toArray(new String[0]);
	}
}
