package tp1.control;

import tp1.exceptions.CommandParseException;

public abstract class ParamsCommand extends Command {
	private int numParams;
	
	ParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	
	/*---GETTERS---*/
	
	int getNumParams() { return this.numParams; }
	
	
	/*---SETTERS---*/
	
	void setNumParams(int n) { this.numParams = n; }
	
	
	/*---CHECKERS---*/
	
	@Override
	abstract Command parse(String[] commandWords) throws CommandParseException;
	
	
	/*---OTHERS---*/
	
	int stringToNumber(String s) throws NumberFormatException { return Integer.valueOf(s); }
	
}
