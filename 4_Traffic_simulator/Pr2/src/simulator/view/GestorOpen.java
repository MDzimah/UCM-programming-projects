package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import messages.Messages;
import simulator.control.Controller;

public class GestorOpen implements ActionListener {

    private Controller ctrl;

    GestorOpen(Controller ctrl) {
        this.ctrl = ctrl;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc;
    	fc = new JFileChooser(System.getProperty("user.dir") + '/' + Messages.EXAMPLES_DIR); 
    	
        int aux = fc.showOpenDialog(null);
        if (aux == JFileChooser.APPROVE_OPTION) {
        	try {
		    	InputStream in = new FileInputStream(fc.getSelectedFile());
		    	this.ctrl.reset();
		    	this.ctrl.loadEvents(in);
        	}
        	catch(Exception ex) {
                JOptionPane.showMessageDialog(null, 
                		Messages.FILE_NOT_FOUND_DIALOG,
                		Messages.FILE_NOT_FOUND_DIALOG_NAME, 
                		JOptionPane.ERROR_MESSAGE);
        	}
        }
	}
}
