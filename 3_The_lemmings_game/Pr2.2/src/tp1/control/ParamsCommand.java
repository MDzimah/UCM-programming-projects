package tp1.control;

public abstract class ParamsCommand extends Command {
	private int numParams;
	
	protected ParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	protected void setNumParams(int n) {
		this.numParams = n;
	}
	
	protected int getNumParams() {
		return this.numParams;
	}
	
	@Override
	protected abstract Command parse(String[] commandWords);
	
	protected boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) { return false; }
	}
	
	protected int stringToNumber(String s) {
		try {
			int n = Integer.valueOf(s);
			return n;
		} catch (NumberFormatException e) { return -1; }
	}
}
