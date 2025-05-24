package messages;

public class Messages {
	//Errors
	public static final String INVALID_CLASS_ARGUMENTS = "The %c's arguments are invalid";
	public static final String INVALID_OBJECT = "Invalid %s";
	public static final String INVALID_OBJECT_REASON = "%s is %r";
	public static final String NOT_IN_VALID_RANGE = "%o is not between %i and %s";
	
	//Tool bar button tool tip texts
	private static final String OPEN_TOOL_TIP = "Load a Simulation";
	private static final String CO2CLASS_TOOL_TIP = "Change CO2 Class of a Vehicle";
	private static final String WEATHER_TOOL_TIP = "Change Weather of a Road";
	private static final String RUN_TOOL_TIP = "Run the Simulator";
	private static final String STOP_TOOL_TIP = "Stop the Simulator";
	private static final String TICK_TOOL_TIP = "Simulation tick to run: 1-10000";
	private static final String EXIT_TOOL_TIP = "Exit the Simulator";
	
	public static String getToolTipMessage(String button_name) {
		switch(button_name.toLowerCase()) {
		case "open": return Messages.OPEN_TOOL_TIP;
		case "co2class": return Messages.CO2CLASS_TOOL_TIP;
		case "weather": return Messages.WEATHER_TOOL_TIP;
		case "run": return Messages.RUN_TOOL_TIP;
		case "stop": return Messages.STOP_TOOL_TIP;
		case "tick": return Messages.TICK_TOOL_TIP;
		default: return Messages.EXIT_TOOL_TIP;
		}
	}

	//Directories
	public static final String BUTTON_ICON_ROOT_DIR = "resources/icons/";
	public static final String EXAMPLES_DIR = "resources/examples/";		

	//JDialogs
	public static final String CHANGE_CO2_DIALOG_NAME = "Change CO2 Class";
	public static final String CHANGE_CO2_DIALOG = "Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now";
	
	public static final String CHANGE_WEATHER_DIALOG_NAME = "Change Road Weather";
	public static final String CHANGE_WEATHER_DIALOG = "Schedule an even to change the weather of a road after a given number of simulation ticks from now";
	
	public static final String RUN_ERROR_DIALOG_NAME = "Run error";
	public static final String RUN_ERROR_DIALOG = "Simulator not initialized or invalid event";
	
	public static final String EXIT_DIALOG_NAME = "Quit";
	public static final String EXIT_DIALOG = "Are you sure you want to quit?";
	
	public static final String FILE_NOT_FOUND_DIALOG_NAME = "File error";
	public static final String FILE_NOT_FOUND_DIALOG = "File not found";
	
	public static final String VEHICLE_ARRIVED_DIALOG_NAME = "Vehicle arrived";
	public static final String VEHICLE_ARRIVED_DIALOG = "Cannot change CO2 class of the vehicle as it has already finished its itinerary";
}