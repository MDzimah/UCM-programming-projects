package tp1.control;

public abstract class NoParamsCommand extends Command {
	public NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}
	
	@Override
	public Command parse(String[] commandWords) {
		if (this.matchCommands(this.toString(commandWords))) {
			return this;
		}
		else return null;
	}
}
