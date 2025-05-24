package tp1.view;

import tp1.util.MyStringUtils;

public class Messages {
	
	public static final String VERSION = "3.0";

	public static final String GAME_NAME = "Lemmings";

	//public static final String USAGE = "Usage: %s [<level>]".formatted(GAME_NAME);

	public static final String WELCOME = String.format("%s %s%n", GAME_NAME, VERSION);

	public static final String LEVEL_NOT_A_NUMBER = "The level must be a number";
	public static final String INVALID_LEVEL_NUMBER = "Not valid level number";

	public static final String LEVEL_NOT_A_NUMBER_ERROR = String.format("%s: %%s", LEVEL_NOT_A_NUMBER);

	public static final String PROMPT = "Command > ";

	public static final String DEBUG = "[DEBUG] Executing: %s%n";
	public static final String ERROR = "[ERROR] Error: %s%n";
	public static final String ERROR_WITHOUT_LINE_SEPARATOR = "[ERROR] Error: %s";
	
	//GAME STATUS
	public static final String NUMBER_OF_CYCLES = "Number of cycles: %s";
	public static final String NUM_LEMMINGS = "Lemmings in board: %s";
	public static final String DEAD_LEMMINGS = "Dead lemmings: %s";
	public static final String EXIT_LEMMINGS = "Lemmings exit door: %s ┃%s";

	//GAME END MESSAGE
	public static final String GAME_OVER = "Game over";
	public static final String PLAYER_QUITS = "Player leaves the game";
	public static final String PLAYER_WINS = "Player wins!";
	public static final String PLAYER_LOSES = "Player loses...";

	//Position format
	public static final String POSITION = "(%s,%s)";

	//Other
	public static final String SPACE = " ";
	public static final String TAB = "   ";
	public static final String LINE_SEPARATOR = System.lineSeparator();
	public static final String LINE = "%s" + LINE_SEPARATOR;
	public static final String LINE_TAB = TAB + LINE;
	public static final String LINE_2TABS = TAB + LINE_TAB;

	
	/*---EXCEPTIONS---*/
	
	//File Errors (Errores de ficheros)
	public static final String FILE_NOT_FOUND = "File not found: \"%s\"";
	public static final String READ_ERROR = "Undetermined error reading file \"%s\"";
	public static final String INVALID_FILE = "Invalid file \"%s\" configuration";
	public static final String FILE_NOT_SAVED = "File \"%s\" incorrectly saved";
	public static final String FILE_SAVED = TAB + "File \"%s\" correctly saved%n";
	
	//Factory Errors (Errores de factorías)
	public static final String UNKNOWN_COMMAND = "Unknown command: %s";
	public static final String UNKNOWN_GAME_OBJECT = "Unknown game object: \"%s\"";
	public static final String UNKNOWN_ROLE = "Unknown role: %s";
	
	//Command Errors (Errores de commandos)
	public static final String COMMAND_INCORRECT_PARAMETER_NUMBER = "Incorrect parameter number";
	public static final String INVALID_PARAMETERS = "Invalid command parameters";
	public static final String INVALID_POSITION = "Invalid position";
	public static final String ERROR_COMMAND_EXECUTE = "Command execute problem";
	public static final String COMMAND_INCORRECT_SETROLE = "No lemming in position %s admits role %s";	
	
	//Lemming Load Error
	public static final String INVALID_LEMMING_HEIGHT = "Invalid lemming height: \"%s\"";
	public static final String INVALID_LEMMING_ROLE = "Invalid lemming role: \"%s\"";
	public static final String INVALID_GAME_STATUS = "Invalid game status \"%s\"";	
	
	//Position and Direction Errors	
	public static final String OFF_THE_BOARD_POSITION = "Position (%s,%s) is off board";
	public static final String ERROR_OBJECT_POSITION_OFF_BOARD = "Object position is off board: \"%s\"";
	public static final String INVALID_OBJECT_POSITION = "Invalid object position: \"%s\""; //IN LOAD ERROR
	public static final String INVALID_LEMMING_DIRECTION = "Invalid lemming direction: \"%s\""; //IN LOAD ERROR
	public static final String ERROR_OBJECT_DIRECTION = "Unknown object direction: \"%s\""; //IN LOAD ERROR
	
	
	/*---COMMANDS---*/
	
	public static final String HELP_AVAILABLE_COMMANDS = "Available commands:";
	public static final String COMMAND_HELP_TEXT = "%s: %s";	
	
	//Set role
	public static final String COMMAND_SET_ROLE_NAME = "setRole";
	public static final String COMMAND_SET_ROLE_SHORTCUT = "sr";
	public static final String COMMAND_SET_ROLE_DETAILS = "[s]et[R]ole ROLE ROW COL";
	public static final String COMMAND_SET_ROLE_HELP = "sets the lemming in position (ROW,COL) to role ROLE";
	
	//Update (none)
	public static final String COMMAND_UPDATE_NAME = "none";
	public static final String COMMAND_UPDATE_SHORTCUT = "n";
	public static final String COMMAND_UPDATE_DETAILS = "[n]one | \"\"";
	public static final String COMMAND_UPDATE_HELP = "user does not perform any action";
	
	//Reset
	public static final String COMMAND_RESET_NAME = "reset";
	public static final String COMMAND_RESET_SHORTCUT = "r";
	public static final String COMMAND_RESET_DETAILS = "[r]eset [numLevel]";
	public static final String COMMAND_RESET_HELP = "reset the game to initial configuration if not numLevel else load the numLevel map";
		
	//Load
	public static final String COMMAND_LOAD_NAME = "load";
	public static final String COMMAND_LOAD_SHORTCUT = "l";
	public static final String COMMAND_LOAD_DETAILS = "[l]oad <fileName>";
	public static final String COMMAND_LOAD_HELP = "load the game configuration from text file <fileName>";
	
	//Save
	public static final String COMMAND_SAVE_NAME = "save";
	public static final String COMMAND_SAVE_SHORTCUT = "s";
	public static final String COMMAND_SAVE_DETAILS = "[s]ave <fileName>";
	public static final String COMMAND_SAVE_HELP = "save the actual configuration in text file <fileName>";
		
	//Help
	public static final String COMMAND_HELP_NAME = "help";
	public static final String COMMAND_HELP_SHORTCUT = "h";
	public static final String COMMAND_HELP_DETAILS = "[h]elp";
	public static final String COMMAND_HELP_HELP = "print this help message";
	
	//Exit
	public static final String COMMAND_EXIT_NAME = "exit";
	public static final String COMMAND_EXIT_SHORTCUT = "e";
	public static final String COMMAND_EXIT_DETAILS = "[e]xit";
	public static final String COMMAND_EXIT_HELP = "exits the game";
	
	
	/*---GAME OBJECT NAMES & SHORTCUTS---*/
	
	public static final String LEMMING_NAME = "lemming";
	public static final String LEMMING_SHORTCUT = "l";
	public static final String WALL_NAME = "wall";
	public static final String WALL_SHORTCUT = "w";
	public static final String METALWALL_NAME = "metalwall";
	public static final String METALWALL_SHORTCUT = "mw";
	public static final String EXIT_DOOR_NAME = "exitdoor";
	public static final String EXIT_DOOR_SHORTCUT = "ed";
	
	
	/*---DIRECTION NAMES---*/
	
	public static final String RIGHT = "right";
	public static final String LEFT = "left";
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String NONE = "none";
	public static final String RIGHT_DOWN = "right_down";
	public static final String LEFT_DOWN = "left_down";
	public static final String RIGHT_UP = "right_up";
	public static final String LEFT_UP = "left_up";
	
	
	/*---ROLES---*/
	
	public static final String WALKER_ROL_SYMBOL = "W";
	public static final String WALKER_ROL_NAME = "Walker";
	public static final String WALKER_ROL_HELP = "[W]alker: Lemming that walks";
	
	public static final String PARACHUTER_ROL_SYMBOL = "P";
	public static final String PARACHUTER_ROL_NAME = "Parachuter";
	public static final String PARACHUTER_ROL_HELP = "[P]arachuter: Lemming falls with a parachute";

	public static final String DOWNCAVER_ROL_SYMBOL = "DC";
	public static final String DOWNCAVER_ROL_NAME = "DownCaver";
	public static final String DOWNCAVER_ROL_HELP = "[D]own [C]aver: Lemming caves downwards";
	
	
	/*---CONSOLE SYMBOLS---*/
	
	public static final String EMPTY = "";
	public static final String METALWALL = MyStringUtils.repeat("X",ConsoleView.CELL_SIZE);
	public static final String WALL = MyStringUtils.repeat("▓",ConsoleView.CELL_SIZE);
	public static final String EXIT_DOOR = "🚪";
	public static final String LEMMING_RIGHT = "B";
	public static final String LEMMING_LEFT = "ᗺ";
	public static final String LEMMING_PARACHUTE = "🪂";
	public static final String LEMMING_DOWN_CAVER = "´･ω･`";
}

